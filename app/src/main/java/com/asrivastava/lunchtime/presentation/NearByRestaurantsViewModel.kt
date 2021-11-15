package com.asrivastava.lunchtime.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asrivastava.lunchtime.common.Resource
import com.asrivastava.lunchtime.domain.model.Restaurant
import com.asrivastava.lunchtime.domain.usecases.GetNearByRestaurantsUseCase
import com.asrivastava.lunchtime.domain.usecases.SearchRestaurantsByKeywordUseCase
import com.asrivastava.lunchtime.domain.usecases.UpdateFavoriteRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearByRestaurantsViewModel @Inject constructor(
    private val getNearByRestaurantsUseCase: GetNearByRestaurantsUseCase,
    private val updateFavoriteRestaurantUseCase: UpdateFavoriteRestaurantUseCase,
    private val searchRestaurantsByKeywordUseCase: SearchRestaurantsByKeywordUseCase,
    private val dispatcher: CoroutineDispatcher = IO,
) : ViewModel() {

    init {
        getNearByRestaurants()
    }

    private val _restaurants = MutableStateFlow(RestaurantListState())
    val restaurants: Flow<RestaurantListState> = _restaurants

    fun onFavoritesClicked(restaurant: Restaurant) {
        viewModelScope.launch(dispatcher) {
            updateFavoriteRestaurantUseCase.invoke(restaurant)
            // Update UI also
            val restaurants = _restaurants.value.restaurants.map { rest ->
                if (rest.id == restaurant.id) {
                    rest.copy(favorite = !rest.favorite)
                } else rest
            }
            _restaurants.emit(RestaurantListState(restaurants))
        }
    }

    fun onSearch(searchKeyword: String = "") {
        viewModelScope.launch(dispatcher) {
            searchRestaurantsByKeywordUseCase.invoke(searchKeyword)
                .onEach { result -> processResult(result) }.collect()
        }
    }

    private fun getNearByRestaurants() {
        viewModelScope.launch(dispatcher) {
            getNearByRestaurantsUseCase().onEach { result -> processResult(result) }.collect()
        }
    }

    private suspend fun processResult(result: Resource<List<Restaurant>>) {
        when (result) {
            is Resource.Success -> {
                Log.d("Asheesh", "Near by place is ${result.data}")
                _restaurants.emit(RestaurantListState(restaurants = result.data ?: emptyList()))
            }
            is Resource.Error -> {
                Log.d("Asheesh", "Near by place Error ${result.message}")
                _restaurants.emit(RestaurantListState(error = result.message))
            }
            is Resource.Loading -> {
                Log.d("Asheesh", "Near by place is Loading")
                _restaurants.emit(RestaurantListState(loading = true))
            }
        }
    }
}
