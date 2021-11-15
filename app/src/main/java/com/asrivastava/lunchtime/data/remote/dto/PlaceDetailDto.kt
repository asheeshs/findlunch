package com.asrivastava.lunchtime.data.remote.dto

import com.asrivastava.lunchtime.domain.model.RestaurantDetail
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailDto(
    @SerialName("html_attributions") val html_attributions: List<@Polymorphic Any>,
    @SerialName("result") val result: PlaceResult,
    val status: String,
)

fun PlaceDetailDto.toRestaurantDetail(): RestaurantDetail {
    return RestaurantDetail(
        name = result.name,
        address = result.formatted_address,
        phoneNumber = result.formatted_phone_number,
        openNow = result.opening_hours.open_now,
        photoId = if (result.photos.isNotEmpty()) result.photos[0].photo_reference else "",
        rating = result.rating.toString(),
        priceLevel = result.price_level?.toString() ?: ""
    )
}
