package com.asrivastava.lunchtime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlusCode(
    val compound_code: String,
    val global_code: String
)
