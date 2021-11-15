package com.asrivastava.lunchtime.domain.repository

import com.asrivastava.lunchtime.data.remote.dto.NearBySearchResponseDto
import com.asrivastava.lunchtime.data.remote.dto.PlaceDetailDto

interface RestaurantRepository {
    suspend fun nearByRestaurants(
        location: String,
        keyword: String, radius: String
    ): NearBySearchResponseDto

    suspend fun restaurantDetail(restaurantId: String): PlaceDetailDto
}
