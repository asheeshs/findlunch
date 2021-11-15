package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)
