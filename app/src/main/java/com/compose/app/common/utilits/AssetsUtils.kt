package com.compose.app.common.utilits

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class AssetsUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun loadJSONFromAsset(fileName: String): String? {
        val json: String?
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


}