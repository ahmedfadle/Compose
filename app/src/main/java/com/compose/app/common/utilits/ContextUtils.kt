package com.compose.app.common.utilits

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import okhttp3.internal.format

/**
 * Extension function to launch Google Maps and navigate to a specified location.
 *
 * This function takes a latitude and longitude, constructs a geo URI, and creates an
 * `Intent` to open Google Maps, showing the location on the map.
 *
 * @param lat The latitude of the location to navigate to.
 * @param log The longitude of the location to navigate to.
 *
 *
 * @sample
 * // Example usage:
 * val latitude = 30.0444  // Latitude for Cairo
 * val longitude = 31.2357  // Longitude for Cairo
 * context.startNavigateToGoogleMaps(latitude, longitude)
 */
fun Context.startNavigateToGoogleMaps(lat:Double,log:Double){
    val uri: String = format("geo:%f,%f", lat, log)
    val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
    startActivity(intent)
}