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

class SearchRestaurantsByKeywordUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val favoriteRestaurantRepository: FavoriteRestaurantRepository,
    private val locationProvider: LocationProvider,
) {
    fun invoke(searchKeyword: String): Flow<Resource<List<Restaurant>>> = flow {
        try {
            emit(Resource.Loading())
            val location = locationProvider.getLocation()
            val result = restaurantRepository.nearByRestaurants(
                location = "${location.lat}, ${location.lng}",
                keyword = searchKeyword,
                radius = Constants.SEARCH_RADIUS
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
