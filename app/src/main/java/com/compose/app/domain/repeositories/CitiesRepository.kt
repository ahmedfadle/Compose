package com.compose.app.domain.repeositories

import com.compose.app.domain.model.City

interface CitiesRepository {
    suspend fun getCities():  List<City>?

}