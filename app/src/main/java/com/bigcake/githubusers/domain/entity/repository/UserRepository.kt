package com.bigcake.githubusers.domain.entity.repository

import com.bigcake.githubusers.domain.PageResult
import com.bigcake.githubusers.domain.Result
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserByPage(nextPage: Int, pageCount: Int): Flow<PageResult<Int, List<User>>>
    fun getUserDetail(login: String): Flow<Result<UserDetail>>
}