package com.compose.app.data.usecases


import com.compose.app.domain.model.City
import com.compose.app.domain.repeositories.CitiesRepository
import javax.inject.Inject

class SearchCitiesUseCaseImp @Inject constructor(private val repository: CitiesRepository) {

    /**
     * Searches for cities that start with the given query string.
     *
     * This function optimizes the search by utilizing a pre-grouped map of cities from the repository,
     * where each key is the first character of a city name (in uppercase) and the value is a list of cities
     * starting with that character. This reduces the search scope significantly, making the operation more efficient,
     * especially when dealing with a large dataset (e.g., 200,000+ entries).
     *
     * Steps:
     * 1. Extract the first character of the query to use as a key.
     * 2. Retrieve the corresponding list of cities from the map.
     * 3. Filter the list to find cities whose names start with the query string (case-insensitive).
     *
     * Example:
     * ```kotlin
     * val result = searchCitiesUseCase("Ca")
     * // Might return mapOf("C" to listOf(Cairo, Casablanca))
     * ```
     *
     * @param query The search query string (e.g., "Ca").
     * @return A map containing the matched cities grouped under the key of their first letter,
     *         or an empty map if no cities match the query.
     */
    suspend operator fun invoke(query: String): Map<String, List<City>> {
        
        val key = query.firstOrNull()?.uppercase() ?: ""
        val filtered = repository.getCities()[key]?.filter {
            it.name.startsWith(query, ignoreCase = true)
        } ?: emptyList()

        return if (filtered.isNotEmpty())
            mapOf(key to filtered)
        else emptyMap()

    }
}