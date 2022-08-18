package com.bigcake.githubusers.domain.entity

data class UserDetail(
    val name: String? = "",
    val bio: String? = "",
    val avatar: String? = null,
    val gitHubId: String,
    val location: String? = null,
    val blog: String? = null,
)