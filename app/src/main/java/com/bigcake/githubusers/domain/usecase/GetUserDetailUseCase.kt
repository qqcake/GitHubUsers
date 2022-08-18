package com.bigcake.githubusers.domain.usecase

import com.bigcake.githubusers.domain.Result
import com.bigcake.githubusers.domain.entity.UserDetail
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userLogin: String): Flow<Result<UserDetail>> {
        return repository.getUserDetail(userLogin)
    }
}