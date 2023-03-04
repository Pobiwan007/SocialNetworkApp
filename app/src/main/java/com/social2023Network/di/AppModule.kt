package com.social2023Network.di

import android.content.Context
import com.social2023Network.domain.repository.HomeRepository
import com.social2023Network.domain.usecase.ConverterDataUseCase
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
        case: ConverterDataUseCase,
    ): HomeViewModelFactory {
        return HomeViewModelFactory(repository, case)
    }

    @Singleton
    @Provides
    fun provideConverterUseCase(): ConverterDataUseCase {
        return ConverterDataUseCase()
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