package com.bigcake.githubusers.di

import com.bigcake.githubusers.data.UserRepositoryImpl
import com.bigcake.githubusers.domain.entity.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}