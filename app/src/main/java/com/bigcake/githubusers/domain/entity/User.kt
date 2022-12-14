package com.bigcake.githubusers.domain.entity

data class User(
    val id: Int,
    val login: String,
    val avatar: String,
    val isSiteAdmin: Boolean,
)
