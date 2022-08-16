package com.bigcake.githubusers.domain.usecase

import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByPage @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(nextPage: Int, pageCount: Int): Flow<Result<Int, List<User>>> {
        return repository.getUserByPage(nextPage, pageCount)
    }
}