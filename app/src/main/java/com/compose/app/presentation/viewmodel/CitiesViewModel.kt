package com.compose.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.app.domain.model.City
import com.compose.app.domain.usecases.GetCitiesUseCase
import com.compose.app.presentation.CitiesIntent
import com.compose.app.presentation.state.CityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val getCitiesUseCase: GetCitiesUseCase) :
    ViewModel() {

    private val _citiesState = MutableStateFlow<CityState>(CityState())
    val citiesState: StateFlow<CityState> get() = _citiesState.asStateFlow()

    private var allOriginalCities: List<City> = emptyList()
    private var allOriginalCitiesMap: Map<String, List<City>> = emptyMap()


    fun onEvent(intent: CitiesIntent) {
        when (intent) {
            is CitiesIntent.EnterQuery -> {
                _citiesState.update { it.copy(query = intent.query) }
                fetchSearchCities(intent.query)
            }

            is CitiesIntent.ClearQuery -> {
                clearSearch()
            }
        }
    }

    private var hasFetched = false

    fun fetchCitiesOnce() {
        if (hasFetched) return
        hasFetched = true
        fetchCities()
    }


    fun fetchCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _citiesState.update { it.copy(isLoading = true, error = null) }

            try {
                val cityList = getCitiesUseCase() ?: emptyList()
                val grouped = cityList.groupBy { it.name.first().uppercase() }

                allOriginalCities = cityList
                allOriginalCitiesMap = grouped

                _citiesState.update {
                    it.copy(
                        isLoading = false,
                        noOfCities = allOriginalCities.size,
                        uiCities = grouped
                    )
                }
            } catch (e: Exception) {
                _citiesState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun fetchSearchCities(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            _citiesState.update { it.copy(isLoading = true, error = null) }

            try {
                val key = query.firstOrNull()?.uppercase() ?: ""
                val filtered = allOriginalCitiesMap[key]?.filter {
                    it.name.startsWith(query, ignoreCase = true)
                } ?: emptyList()

                val grouped = if (filtered.isNotEmpty())
                    mapOf(key to filtered)
                else emptyMap()

                _citiesState.update {
                    it.copy(
                        isLoading = false,
                        noOfCities = filtered.size,
                        uiCities = grouped
                    )
                }
            } catch (e: Exception) {
                _citiesState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }


    private fun clearSearch() {
        _citiesState.update { it.copy(query = "") }

        _citiesState.update {
            it.copy(
                uiCities = allOriginalCitiesMap,
                noOfCities = allOriginalCities.size,
                isLoading = false,
                error = null
            )
        }
    }
}