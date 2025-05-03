package com.compose.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.app.data.usecases.GetCitiesUseCaseImp
import com.compose.app.data.usecases.SearchCitiesUseCaseImp
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
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCaseImp
    ,private val searchCitiesUseCaseImp : SearchCitiesUseCaseImp)
    : ViewModel() {

    private var hasFetched = false
    private val _citiesState = MutableStateFlow<CityState>(CityState())
    val citiesState: StateFlow<CityState> get() = _citiesState.asStateFlow()

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

    fun fetchCitiesOnce() {
        if (hasFetched) return
        hasFetched = true
        fetchCities()
    }


    /**
     * Fetches and prepares the list of cities to be displayed in the UI.
     *
     * This function launches a coroutine on the IO dispatcher to retrieve a map of cities using [getCitiesUseCase],
     * where each entry is grouped by the first character of the city name.
     *
     * - While the data is being fetched, the UI state is updated to show a loading indicator.
     * - On successful retrieval:
     *   - `uiCities` is updated with the grouped city map.
     *   - `noOfCities` reflects the total number of cities.
     * - On failure, the error message is captured and displayed in the state.
     *
     * The result is stored in [_citiesState], a [StateFlow] used to render the UI.
     */
    fun fetchCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _citiesState.update { it.copy(isLoading = true, error = null) }

            try {
                val cityList = getCitiesUseCase()

                _citiesState.update {
                    it.copy(
                        isLoading = false,
                        noOfCities = cityList.values.flatten().size,
                        uiCities = cityList
                    )
                }
            } catch (e: Exception) {
                _citiesState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    /**
     * Searches for cities matching the provided [query] and updates the UI state accordingly.
     *
     * This function:
     * - Validates the [query] to ensure it's not blank. If blank, it calls [clearSearch] and returns.
     * - Launches a coroutine on the Default dispatcher to perform the search in a background thread.
     * - Calls [searchCitiesUseCaseImp] with the query, which searches within a subset of cities grouped
     *   by the first character of the query. This approach improves performance by reducing the search scope.
     * - Updates [_citiesState] with:
     *   - `isLoading = true` at the start.
     *   - On success: the filtered map of matching cities (`uiCities`) and the total count (`noOfCities`).
     *   - On failure: handled implicitly if [searchCitiesUseCaseImp] throws (optional to handle explicitly).
     *
     * @param query The search term entered by the user to filter cities by name.
     */
    private fun fetchSearchCities(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            _citiesState.update { it.copy(isLoading = true, error = null) }
            val result = searchCitiesUseCaseImp(query)
            _citiesState.update {
                it.copy(
                    isLoading = false,
                    noOfCities = result.values.flatten().size,
                    uiCities = result
                )
            }
        }
    }


    private fun clearSearch() {
        _citiesState.update { it.copy(query = "") }
        fetchCities()
    }
}
