package com.compose.app.data.di

import com.compose.app.data.repositories.CitiesRepositoryImp
import com.compose.app.data.usecases.GetCitiesUseCaseImp
import com.compose.app.domain.repeositories.CitiesRepository
import com.compose.app.domain.usecases.GetCitiesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCitiesRepository(
        impl: CitiesRepositoryImp
    ): CitiesRepository
}