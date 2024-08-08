package com.denish.mob21firebase.core.di

import com.denish.mob21firebase.core.service.AuthService
import com.denish.mob21firebase.data.repo.TodoRepo
import com.denish.mob21firebase.data.repo.TodoRepoFirestore
import com.denish.mob21firebase.data.repo.TodoRepoReal
import com.denish.mob21firebase.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideTodoRepo(authService: AuthService): TodoRepo {
        return TodoRepoFirestore(authService)
    }

    @Provides
    @Singleton
    fun provideUserRepo(): UserRepo {
        return UserRepo()
    }
}