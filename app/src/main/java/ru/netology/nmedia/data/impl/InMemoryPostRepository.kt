package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository:PostRepository {
    private val posts
        get() = checkNotNull(data.value) {
            "Data value not be null"
        }

    override val data = MutableLiveData(
        List(10){index ->
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
        data.value =posts.map {
            if (it.id != postId) it
            else it.copy(likedByMe = !it.likedByMe)
        }
    }

    override fun share(postId: Long){
        data.value =posts.map {
            if (it.id != postId) it
            else it.copy(shares = it.shares + 10)
        }
    }
}