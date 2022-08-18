package com.bigcake.githubusers.data.remote.network

import com.bigcake.githubusers.BuildConfig
import com.bigcake.githubusers.data.remote.dto.UserDetailDto
import com.bigcake.githubusers.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserDto>>

    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String
    ): UserDetailDto
}