package com.asrivastava.lunchtime.data.remote

import com.asrivastava.lunchtime.data.remote.dto.NearBySearchResponseDto
import com.asrivastava.lunchtime.data.remote.dto.PlaceDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlaceApi {

    @GET("nearbysearch/json")
    suspend fun nearBySearch(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String,
    ): NearBySearchResponseDto

    @GET("details/json")
    suspend fun placeDetails(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String,
    ): PlaceDetailDto
}
