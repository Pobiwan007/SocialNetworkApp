package com.social2023Network.di

import android.content.Context
import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.data.repository.HomeRepository
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.BaseApplication
import com.social2023Network.presentation.ui.home.HomeViewModelFactory
import com.social2023Network.presentation.ui.util.DialogManager
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
    fun provideHomeViewModelFactory(
        repository: HomeRepository,
        homeUseCase: HomeUseCase
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

    @Singleton
    @Provides
    fun provideFireBaseDB(): FirebaseManager {
        return FirebaseManager.getInstance()
    }

    @Singleton
    @Provides
    fun provideDialogManager() : DialogManager{
        return DialogManager()
    }

    @Singleton
    @Provides
    fun provideHomeRepository(
        firebaseManager: FirebaseManager,
        homeUseCase: HomeUseCase,
        dialogManager: DialogManager
    ): HomeRepository {
        return HomeRepository(firebaseManager, homeUseCase, dialogManager)
    }
}