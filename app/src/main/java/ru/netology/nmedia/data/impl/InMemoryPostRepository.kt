package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository:PostRepository {
    override val data = MutableLiveData<Post>(
        Post(
            id = 1,
            author = "Sasha",
            content = "Events",
            published = "07.05.2022",
            likes = 111
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value not be null"
        }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe
        )
        data.value = if(likedPost.likedByMe){
            likedPost.copy(likes = likedPost.likes + 1)
        } else {
            likedPost.copy(likes = likedPost.likes - 1)
        }
    }

    override fun share(){
        val currentPost = checkNotNull(data.value) {
            "Data value not be null"
        }
        val sharedPost = currentPost.copy(
            shares = currentPost.shares + 10
        )
        data.value = sharedPost
    }
}