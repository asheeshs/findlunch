package com.asrivastava.lunchtime.domain.usecases

import com.asrivastava.lunchtime.common.Constants
import com.asrivastava.lunchtime.common.Resource
import com.asrivastava.lunchtime.data.remote.dto.toRestaurants
import com.asrivastava.lunchtime.domain.model.Restaurant
import com.asrivastava.lunchtime.domain.repository.FavoriteRestaurantRepository
import com.asrivastava.lunchtime.domain.repository.RestaurantRepository
import com.asrivastava.lunchtime.domain.util.LocationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNearByRestaurantsUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val favoriteRestaurantRepository: FavoriteRestaurantRepository,
    private val locationProvider: LocationProvider,
) {
    operator fun invoke(): Flow<Resource<List<Restaurant>>> = flow {
        try {
            emit(Resource.Loading())
            val location = locationProvider.getLocation()
            val result = restaurantRepository.nearByRestaurants(
                location = "${location.lat}, ${location.lng}",
                keyword = "",
                radius = Constants.RADIUS
            ).toRestaurants()
                .map { restaurant ->
                    if (favoriteRestaurantRepository.getFavoriteRestaurants().contains(restaurant.id)) {
                        restaurant.copy(favorite = true)
                    } else restaurant
                }
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Something went wrong."))
        }
    }
}
