package com.bigcake.githubusers.data

import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

) : UserRepository {
    private val dummyUsers = (0..99).map {
        User(
            id = it,
            login = "Login $it"
        )
    }

    override fun getUserByPage(nextPage: Int, pageCount: Int): Flow<Result<Int, List<User>>> =
        flow {
            val users = if (nextPage + pageCount > dummyUsers.size) {
                emptyList()
            } else {
                dummyUsers.slice(nextPage until nextPage + pageCount)
            }
            val next = if (users.isEmpty()) nextPage else users.last().id + 1
            emit(Result.Page(next, users))
        }
}