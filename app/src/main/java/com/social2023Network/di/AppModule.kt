package com.social2023Network.di

import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.presentation.ui.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideHomeRepository() : HomeRepository {
        return HomeRepository()
    }

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(repository: HomeRepository): HomeViewModelFactory{
        return HomeViewModelFactory(repository)
    }

}