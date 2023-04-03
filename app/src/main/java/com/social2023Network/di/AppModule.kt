package com.social2023Network.di

import com.social2023Network.data.firebase.FirebaseManager
import com.social2023Network.data.repository.FirebaseAuthRepositoryImpl
import com.social2023Network.data.repository.HomeRepositoryImpl
import com.social2023Network.domain.usecase.HomeUseCase
import com.social2023Network.presentation.BaseApplication
import com.social2023Network.presentation.ui.auth.AuthViewModelFactory
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
    fun provideConverterUseCase(): HomeUseCase {
        return HomeUseCase()
    }


    @Singleton
    @Provides
    fun provideBaseApplication() = BaseApplication()


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
    fun provideHomeRepositoryImpl(
        firebaseManager: FirebaseManager,
        homeUseCase: HomeUseCase,
        dialogManager: DialogManager
    ): HomeRepositoryImpl {
        return HomeRepositoryImpl(firebaseManager, homeUseCase, dialogManager)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthRepositoryImpl(
        firebaseManager: FirebaseManager
    ): FirebaseAuthRepositoryImpl{
        return FirebaseAuthRepositoryImpl(firebaseManager)
    }


    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        repository: HomeRepositoryImpl,
        homeUseCase: HomeUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(repository, homeUseCase)
    }

    @Singleton
    @Provides
    fun provideAuthViewModelFactory(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): AuthViewModelFactory{
        return AuthViewModelFactory(firebaseAuthRepositoryImpl)
    }
}