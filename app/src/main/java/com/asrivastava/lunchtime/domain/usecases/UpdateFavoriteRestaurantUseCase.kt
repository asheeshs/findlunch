package com.asrivastava.lunchtime.domain.usecases

import com.asrivastava.lunchtime.domain.model.Restaurant
import com.asrivastava.lunchtime.domain.repository.FavoriteRestaurantRepository
import javax.inject.Inject

class UpdateFavoriteRestaurantUseCase @Inject constructor(
    private val favoriteRestaurantRepository: FavoriteRestaurantRepository
) {
    suspend fun invoke(restaurant: Restaurant) {
        if (restaurant.favorite) {
            favoriteRestaurantRepository.removeFavoriteRestaurant(
                restaurantId = restaurant.id,
            )
        } else {
            favoriteRestaurantRepository.addFavoriteRestaurant(
                restaurantId = restaurant.id,
                restaurantName = restaurant.name
            )
        }
    }
}
