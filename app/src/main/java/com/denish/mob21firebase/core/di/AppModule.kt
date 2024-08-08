package com.denish.mob21firebase.core.di

import android.content.Context
import com.denish.mob21firebase.core.service.AuthService
import com.denish.mob21firebase.core.service.StorageService
import com.denish.mob21firebase.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthService(@ApplicationContext context: Context): AuthService {
        return  AuthService(context = context)
    }

    @Provides
    @Singleton
    fun provideStorageService(authService: AuthService): StorageService {
        return StorageService(authService)
    }

}