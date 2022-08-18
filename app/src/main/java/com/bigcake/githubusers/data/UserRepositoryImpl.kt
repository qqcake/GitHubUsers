package com.bigcake.githubusers.data

import com.bigcake.githubusers.data.mapper.toDomain
import com.bigcake.githubusers.data.remote.network.GitHubApi
import com.bigcake.githubusers.data.util.LinkHeaderHelper
import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : UserRepository {
    override fun getUserByPage(nextPage: Int, pageCount: Int): Flow<Result<Int, List<User>>> =
        flow {
            try {
                val response = gitHubApi.getUsers(nextPage, pageCount)
                if (response.isSuccessful) {
                    val linkHeader = response.headers()["Link"]
                    val userDtoList = response.body()
                    userDtoList?.let {
                        val since = LinkHeaderHelper.parse(linkHeader)["next"]?.since ?: nextPage
                        emit(Result.Page(since, userDtoList.map { it.toDomain() }))
                    } ?: run {
                        val message = response.errorBody()?.toString() ?: "Invalid response"
                        emit(Result.Failure(message, emptyList()))
                    }
                } else {
                    val message = response.errorBody()?.toString() ?: "Unknown error"
                    emit(Result.Failure(message, emptyList()))
                }
            } catch (e: UnknownHostException) {
                val message = e.message?.let {
                    "Seems network is not available, please make sure network is available."
                } ?: "Unknown error"
                emit(Result.Failure(message, emptyList()))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Failure(e.message ?: "Unknown error", emptyList()))
            }
        }
}