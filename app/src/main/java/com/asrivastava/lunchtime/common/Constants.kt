package com.asrivastava.lunchtime.common

import com.asrivastava.lunchtime.BuildConfig

object Constants {
    const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
    const val API_KEY = BuildConfig.GMP_KEY
    const val TYPE = "restaurant"
    const val RADIUS = "5000"
    const val SEARCH_RADIUS = "25000"

    const val DATABASE_NAME = "fav-restaurants-db"

    val FIELDS = listOf(
        "name",
        "rating",
        "formatted_phone_number",
        "photo",
        "formatted_address",
        "geometry",
        "opening_hours/open_now",
        "price_level",
    )
}
