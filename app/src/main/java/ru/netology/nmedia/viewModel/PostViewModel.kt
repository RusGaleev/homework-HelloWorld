package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionsListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel:ViewModel(), PostInteractionsListener {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val editPostContentScreenEvent = SingleLiveEvent<String>()

    /**
     *Значение события содержит url для воспроизведеиня
     *  */
    val playVideo = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClick(content:String){
        if (content.isBlank()) return

        val newPost =currentPost.value?.copy(
            content = content
        )?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
            video = null
        )
        repository.save(newPost)
        currentPost.value = null
    }

    fun onAddClicked(){
        navigateToPostContentScreenEvent.call()
    }

    // region PostInteractionsListener
    override fun onShareClicked(post:Post) = repository.share(post.id)
    override fun onLikeClicked(post:Post) = repository.like(post.id)
    override fun onDeleteClicked(post:Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        editPostContentScreenEvent.value = post.content
    }
    override fun onPlayVideoClicked(post: Post) {
        val url:String = requireNotNull(post.video){
            "Video url is broken"
        }
        playVideo.value = url
    }

    // endregion PostInteractionsListener
}