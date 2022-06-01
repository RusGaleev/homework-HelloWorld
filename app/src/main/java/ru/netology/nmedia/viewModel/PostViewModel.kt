package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionsListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application): AndroidViewModel(application), PostInteractionsListener {
    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )

    val data by repository::data
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val sharePostContent = SingleLiveEvent<String>()
    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClick(content:String){
        if (content.isBlank()) return

        val newPost =currentPost.value?.copy(
            content = content
        )?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today"
        )
        repository.save(newPost)
        currentPost.value = null
    }

    fun onAddClicked(){
        navigateToPostContentScreenEvent.call()
    }

    // region PostInteractionsListener
    override fun onShareClicked(post:Post) = repository.share(post.id)
//    sharePostContent.value = post.content
    override fun onLikeClicked(post:Post) = repository.like(post.id)
    override fun onDeleteClicked(post:Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content}

    // endregion PostInteractionsListener
}