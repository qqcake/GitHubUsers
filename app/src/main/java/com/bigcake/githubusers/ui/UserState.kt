package com.bigcake.githubusers.ui

import com.bigcake.githubusers.domain.entity.User

data class UserState(
    val users: List<User> = emptyList(),
    val filteredUsers: List<User> = emptyList(),
    val nextPageKey: Int = 0,
    val loginFilterText: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)