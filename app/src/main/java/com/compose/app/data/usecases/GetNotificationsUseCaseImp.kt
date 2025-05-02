package com.compose.app.data.usecases


import com.compose.app.domain.repeositories.CitiesRepository
import com.compose.app.domain.usecases.GetCitiesUseCase
import javax.inject.Inject

class GetCitiesUseCaseImp @Inject constructor(private val repository: CitiesRepository) :
    GetCitiesUseCase {
    override suspend operator fun invoke() = repository.getCities()?.sortedBy { it.name }


}