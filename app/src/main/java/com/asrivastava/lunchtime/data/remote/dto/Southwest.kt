package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Southwest(
    val lat: Double,
    val lng: Double
)
