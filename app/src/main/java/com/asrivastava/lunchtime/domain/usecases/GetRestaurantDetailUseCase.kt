package com.asrivastava.lunchtime.domain.usecases

import com.asrivastava.lunchtime.common.Resource
import com.asrivastava.lunchtime.data.remote.dto.toRestaurantDetail
import com.asrivastava.lunchtime.domain.model.RestaurantDetail
import com.asrivastava.lunchtime.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantDetailUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantId: String
) {
    operator fun invoke(): Flow<Resource<RestaurantDetail>> = flow {
        try {
            emit(Resource.Loading())
            val result = restaurantRepository.restaurantDetail(restaurantId).toRestaurantDetail()
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Something went wrong."))
        }
    }
}
