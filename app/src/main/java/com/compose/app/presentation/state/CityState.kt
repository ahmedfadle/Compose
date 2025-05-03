package com.compose.app.presentation.state

import com.compose.app.domain.model.City

data class CityState(
    val isLoading: Boolean = false,
    val uiCities: Map<String,List<City>> = emptyMap(),
    val noOfCities: Int = 0,
    val query: String = "",
    val error: String? = null
)
