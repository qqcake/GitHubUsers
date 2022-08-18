package com.bigcake.githubusers.domain

sealed class PageResult<Key, T> {
    data class Page<Key, T>(val next: Key, val data: T) : PageResult<Key, T>()
    data class Failure<Key, T>(val message: String, val data: T? = null) : PageResult<Key, T>()
}

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val message: String, val data: T? = null) : Result<T>()
}
