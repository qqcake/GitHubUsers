package com.bigcake.githubusers.domain.entity.repository

import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserByPage(nextPage: Int, pageCount: Int): Flow<Result<Int, List<User>>>
}