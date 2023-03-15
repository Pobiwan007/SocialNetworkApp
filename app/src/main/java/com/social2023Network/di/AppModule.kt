package com.social2023Network.di

import android.content.Context
import com.social2023Network.domain.repository.HomeRepository
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.BaseApplication
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
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        repository: HomeRepository,
        homeUseCase: HomeUseCase,
    ): HomeViewModelFactory {
        return HomeViewModelFactory(repository, homeUseCase)
    }

    @Singleton
    @Provides
    fun provideConverterUseCase(): HomeUseCase {
        return HomeUseCase()
    }


    @Singleton
    @Provides
    fun provideBaseApplication() = BaseApplication()

    @Provides
    @Singleton
    fun provideApplicationContext(baseApplication: BaseApplication): Context {
        return baseApplication
    }

}