package com.bigcake.githubusers.domain.usecase

import com.bigcake.githubusers.domain.PageResult
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(nextPage: Int, perPage: Int): Flow<PageResult<Int, List<User>>> {
        return repository.getUserByPage(nextPage, perPage)
    }
}