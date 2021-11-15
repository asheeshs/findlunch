package com.asrivastava.lunchtime.presentation

import com.asrivastava.lunchtime.domain.model.Restaurant

data class RestaurantListState(
    val restaurants: List<Restaurant> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)


