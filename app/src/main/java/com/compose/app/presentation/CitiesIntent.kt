package com.compose.app.presentation



sealed interface CitiesIntent {
    data class EnterQuery(val query: String) : CitiesIntent
    data object ClearQuery : CitiesIntent

}