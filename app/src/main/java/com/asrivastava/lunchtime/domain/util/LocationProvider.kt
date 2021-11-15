package com.asrivastava.lunchtime.domain.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.asrivastava.lunchtime.domain.model.Location

class LocationProvider constructor(private val context: Context) {

    val hasLocationPermission: Boolean
        get() {
            return ContextCompat.checkSelfPermission(
                context, LOCATION_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED
        }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location {
        return if (hasLocationPermission) {
            val locationManager: LocationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
            val location = locationManager.allProviders.mapNotNull { locationManager.getLastKnownLocation(it) }.firstOrNull()
            Log.d("Asheesh", "Last known location: $location")
            location?.let { Location(it.latitude, it.longitude) } ?: Location()
        } else Location()
    }

    companion object {
        const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}
