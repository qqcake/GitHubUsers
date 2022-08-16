package com.bigcake.githubusers.domain.entity

import java.security.Key

sealed class Result<Key, T> {
    data class Page<Key, T>(val next: Key, val data: T) : Result<Key, T>()
    data class Failure<Key, T>(val message: String, val data: T? = null) : Result<Key, T>()
}
