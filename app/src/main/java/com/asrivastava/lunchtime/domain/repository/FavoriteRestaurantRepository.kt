package com.asrivastava.lunchtime.domain.repository

interface FavoriteRestaurantRepository {
    suspend fun getFavoriteRestaurants(): Set<String>
    suspend fun addFavoriteRestaurant(restaurantId: String, restaurantName: String)
    suspend fun removeFavoriteRestaurant(restaurantId: String)
}
