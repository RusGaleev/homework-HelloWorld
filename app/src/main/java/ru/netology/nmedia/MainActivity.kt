package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Sasha",
            content = "Events",
            published = "07.05.2022"
        )

        binding.render(post)
        binding.postFavoriteButton.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.postFavoriteButton.setImageResource(getLikeIconResId(post.likedByMe))
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        postAuthorName.text = post.author
        postText.text = post.content
        postDate.text = post.published
        postFavoriteButton.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_favorite_24dp else R.drawable.ic_favorite_border_24dp

}