package com.compose.app.domain.usecases

import com.compose.app.domain.model.City

interface GetCitiesUseCase {

    suspend operator fun invoke(): List<City>?


}