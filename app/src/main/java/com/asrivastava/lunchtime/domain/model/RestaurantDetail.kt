package com.asrivastava.lunchtime.domain.model

data class RestaurantDetail(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val openNow: Boolean,
    val photoId: String,
    val rating: String,
    val priceLevel: String,
)
