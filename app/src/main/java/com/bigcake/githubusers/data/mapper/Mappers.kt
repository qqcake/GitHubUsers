package com.bigcake.githubusers.data.mapper

import com.bigcake.githubusers.data.remote.dto.UserDetailDto
import com.bigcake.githubusers.data.remote.dto.UserDto
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.UserDetail

fun UserDto.toDomain(): User {
    return User(id, login, avatarUrl, siteAdmin)
}

fun UserDetailDto.toDomain(): UserDetail {
    return UserDetail(
        name = name,
        avatar = avatarUrl,
        bio = bio,
        gitHubId = login ?: "",
        location = location,
        blog = blog,
    )
}