package com.compose.app.domain.model

data class City(
    val _id: Int,
    val coord: Coord,
    val country: String,
    val name: String
)