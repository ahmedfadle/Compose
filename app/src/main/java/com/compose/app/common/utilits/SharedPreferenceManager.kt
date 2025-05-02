package com.compose.app.common.utilits

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "AppPreferences"
        @Volatile
        private var instance: SharedPreferenceManager? = null

        fun getInstance(context: Context): SharedPreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferenceManager(context).also { instance = it }
            }
        }
    }

    fun <T> put(key: String, value: T) {
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }
        editor.apply()
    }

    fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    fun clear() {
        editor.clear().apply()
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}
