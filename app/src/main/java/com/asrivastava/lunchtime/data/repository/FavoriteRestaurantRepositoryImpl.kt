package com.asrivastava.lunchtime.data.repository

import com.asrivastava.lunchtime.data.database.FavoriteRestaurant
import com.asrivastava.lunchtime.data.database.FavoriteRestaurantDao
import com.asrivastava.lunchtime.domain.repository.FavoriteRestaurantRepository
import javax.inject.Inject

class FavoriteRestaurantRepositoryImpl @Inject constructor(
    private val favoriteRestaurantDao: FavoriteRestaurantDao
) : FavoriteRestaurantRepository {
    override suspend fun getFavoriteRestaurants(): Set<String> {
        return favoriteRestaurantDao.getFavoriteRestaurants().toSet()
    }

    override suspend fun addFavoriteRestaurant(restaurantId: String, restaurantName: String) {
        favoriteRestaurantDao.addFavoriteRestaurant(
            FavoriteRestaurant(id = restaurantId, name = restaurantName)
        )
    }

    override suspend fun removeFavoriteRestaurant(restaurantId: String) {
        favoriteRestaurantDao.deleteFavoriteRestaurant(id = restaurantId)
    }
}
