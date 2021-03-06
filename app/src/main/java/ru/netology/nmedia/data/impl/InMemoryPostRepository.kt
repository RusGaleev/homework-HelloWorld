package ru.netology.nmedia.data.impl


import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private var nextId = GENERATED_POST_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "Data value not be null"
        }

    override val data = MutableLiveData(
        List(GENERATED_POST_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content $index",
                published = "15.05.2022",
                likes = 111
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else {
                if (it.likedByMe) it.copy(likes = it.likes - 1, likedByMe = !it.likedByMe)
                else it.copy(likes = it.likes + 1, likedByMe = !it.likedByMe)
            }
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shares = it.shares + 10)
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post:Post){
        data.value = listOf(
            post.copy(
                id =++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map{
            if(it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 1000
    }

}
