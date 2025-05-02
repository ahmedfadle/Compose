package com.compose.app.data.di

import com.compose.app.data.repositories.CitiesRepositoryImp
import com.compose.app.data.usecases.GetCitiesUseCaseImp
import com.compose.app.domain.usecases.GetCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideGetCitiesUseCase(repository: CitiesRepositoryImp): GetCitiesUseCase {
        return GetCitiesUseCaseImp(repository)
    }



}