package com.bigcake.githubusers.presentation.userdetail

import com.bigcake.githubusers.domain.entity.UserDetail

data class UserDetailState(
    val user: UserDetail? = null,
    val error: String? = null,
)