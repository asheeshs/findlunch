package com.asrivastava.lunchtime.data.repository

import com.asrivastava.lunchtime.common.Constants
import com.asrivastava.lunchtime.data.remote.GooglePlaceApi
import com.asrivastava.lunchtime.data.remote.dto.NearBySearchResponseDto
import com.asrivastava.lunchtime.data.remote.dto.PlaceDetailDto
import com.asrivastava.lunchtime.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val api: GooglePlaceApi,
) : RestaurantRepository {
    override suspend fun nearByRestaurants(
        location: String,
        keyword: String,
        radius: String
    ): NearBySearchResponseDto {
        return api.nearBySearch(
            location = location,
            radius = radius,
            type = Constants.TYPE,
            keyword = keyword,
            apiKey = Constants.API_KEY
        )
    }

    override suspend fun restaurantDetail(
        restaurantId: String,
    ): PlaceDetailDto {
        return api.placeDetails(
            placeId = restaurantId,
            fields = Constants.FIELDS.joinToString(separator = ","),
            apiKey = Constants.API_KEY
        )
    }
}
