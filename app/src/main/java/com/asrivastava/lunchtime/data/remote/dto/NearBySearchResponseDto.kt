package com.asrivastava.lunchtime.data.remote.dto

import com.asrivastava.lunchtime.domain.model.Location
import com.asrivastava.lunchtime.domain.model.Restaurant
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearBySearchResponseDto(
    @SerialName("html_attributions") val html_attributions: List<@Polymorphic Any>,
    val results: List<Result>,
    val status: String,
)

fun NearBySearchResponseDto.toRestaurants(): List<Restaurant> {
    return results.map {
        Restaurant(
            location = Location(it.geometry.location.lat, it.geometry.location.lng),
            northEast = Location(it.geometry.viewport.northeast.lat, it.geometry.viewport.northeast.lng),
            southWest = Location(it.geometry.viewport.southwest.lat, it.geometry.viewport.southwest.lng),
            businessStatus = it.business_status,
            name = it.name,
            id = it.place_id,
            rating = it.rating,
            totalUserRatings = it.user_ratings_total,
            priceLevel = it.price_level
        )
    }
}
