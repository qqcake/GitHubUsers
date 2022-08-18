package com.bigcake.githubusers.presentation

sealed class Screen(val route: String) {
    object UserListScreen : Screen("user_list")
    object UserDetailScreen : Screen("user_detail")
}
