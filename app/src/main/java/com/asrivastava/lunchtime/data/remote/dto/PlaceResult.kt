package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResult(
    val formatted_address: String,
    val formatted_phone_number: String,
    val geometry: Geometry,
    val name: String,
    val opening_hours: OpeningHours,
    val photos: List<Photo>,
    val price_level: Int? = null,
    val rating: Double,
)
