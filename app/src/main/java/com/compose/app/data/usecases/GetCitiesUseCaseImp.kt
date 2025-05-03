package com.compose.app.data.usecases


import com.compose.app.domain.repeositories.CitiesRepository
import javax.inject.Inject

class GetCitiesUseCaseImp @Inject constructor(private val repository: CitiesRepository) {
     suspend operator fun invoke() = repository.getCities().toSortedMap()
}