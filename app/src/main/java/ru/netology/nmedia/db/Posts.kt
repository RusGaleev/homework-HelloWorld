package ru.netology.nmedia.db

import ru.netology.nmedia.data.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    likes = likes,
//    shares = shares,
    published = published,
    likedByMe = likedByMe
    )

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    likes = likes,
//    shares = shares,
    published = published,
    likedByMe = likedByMe
)