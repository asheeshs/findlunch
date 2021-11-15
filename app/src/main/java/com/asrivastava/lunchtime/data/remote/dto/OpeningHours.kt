package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OpeningHours(
    val open_now: Boolean
)
