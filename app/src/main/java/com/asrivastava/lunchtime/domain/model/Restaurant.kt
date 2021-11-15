package com.asrivastava.lunchtime.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String = "id",
    val name: String = "Restaurant name",
    val rating: Double = 0.0,
    val totalUserRatings: Int = 236,
    val businessStatus: String = "BUSINESS STATUS",
    val priceLevel: Int? = null,
    val location: Location = Location(),
    val northEast: Location = Location(),
    val southWest: Location = Location(),
    val favorite: Boolean = false,
)
