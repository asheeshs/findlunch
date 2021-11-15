package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val location: Location,
    val viewport: Viewport
)
