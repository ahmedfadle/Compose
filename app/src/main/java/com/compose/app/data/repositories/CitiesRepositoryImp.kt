package com.compose.app.data.repositories

import com.compose.app.common.Constants
import com.compose.app.common.utilits.AssetsUtils
import com.compose.app.domain.model.City
import com.compose.app.domain.repeositories.CitiesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepositoryImp @Inject constructor(
    private val assetsUtils: AssetsUtils
) : CitiesRepository {

    private var allOriginalCitiesMap: Map<String, List<City>> = emptyMap()

    /**
     * Loads and returns a map of cities grouped by the first uppercase letter of their names.
     *
     * This function first checks if the cached city map (`allOriginalCitiesMap`) is already populated.
     * If so, it returns the cached result. Otherwise, it:
     * - Loads a JSON file containing a list of cities from the app's assets.
     * - Deserializes the JSON into a list of `City` objects using Gson.
     * - Groups the cities by the first uppercase letter of their name.
     * - Caches the grouped result for future calls.
     *
     * @return A map where each key is an uppercase letter (A-Z), and the value is a list of cities
     *         whose names start with that letter. If an error occurs, an empty map is returned.
     */
    override suspend fun getCities(): Map<String, List<City>> {
        if (allOriginalCitiesMap.isNotEmpty())
            return allOriginalCitiesMap

        return runCatching {
            val json = assetsUtils.loadJSONFromAsset(Constants.CITIES_JSON_FILE_NAME)
            val type = object : TypeToken<List<City>>() {}.type
            val cities: List<City> = Gson().fromJson(json, type)
           val grouped = cities.groupBy { it.name.first().uppercase() }
            allOriginalCitiesMap= grouped
            grouped
        }.getOrElse {
            emptyMap()
        }
    }
}
