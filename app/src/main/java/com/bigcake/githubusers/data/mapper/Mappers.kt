package com.bigcake.githubusers.data.mapper

import com.bigcake.githubusers.data.remote.dto.UserDto
import com.bigcake.githubusers.domain.entity.User

fun UserDto.toDomain(): User {
    return User(id, login, avatarUrl, siteAdmin)
}