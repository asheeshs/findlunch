package com.asrivastava.lunchtime.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(val lat: Double = 0.0, val lng: Double = 0.0)
