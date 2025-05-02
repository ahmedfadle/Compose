package com.compose.app.data.repositories

import com.compose.app.common.utilits.AssetsUtils
import com.compose.app.domain.model.City
import com.compose.app.domain.repeositories.CitiesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CitiesRepositoryImp @Inject constructor(
    private val assetsUtils: AssetsUtils
) : CitiesRepository {


    override suspend fun getCities(): List<City>? {
        return try {
            val jsonString = assetsUtils.loadJSONFromAsset("cities.json")
            val cityType = object : TypeToken<List<City>>() {}.type
            Gson().fromJson(jsonString, cityType)
        }catch (e:Exception){
            null
        }
    }

}