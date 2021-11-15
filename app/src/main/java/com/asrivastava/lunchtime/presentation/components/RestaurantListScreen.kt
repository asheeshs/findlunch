package com.asrivastava.lunchtime.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asrivastava.lunchtime.R
import com.asrivastava.lunchtime.domain.model.Restaurant
import com.asrivastava.lunchtime.presentation.NearByRestaurantsViewModel
import com.asrivastava.lunchtime.presentation.RestaurantListState

@Composable
fun RestaurantListScreen(
    viewModel: NearByRestaurantsViewModel,
    onMapViewButtonClick: () -> Unit,
) {
    val state by viewModel.restaurants.collectAsState(initial = RestaurantListState())
    RestaurantListScreenUI(
        state = state,
        onMapViewButtonClick = onMapViewButtonClick,
        onFavoritesClicked = viewModel::onFavoritesClicked
    )
}

@Composable
fun RestaurantListScreenUI(
    state: RestaurantListState,
    onMapViewButtonClick: () -> Unit,
    onFavoritesClicked: (restaurant: Restaurant) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.restaurants) { restaurant ->
                RestaurantCard(restaurant, true) {
                    onFavoritesClicked(restaurant)
                }
            }
        }

        if (state.error?.isNotBlank() == true) {
            Text(
                text = state.error ?: "",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (!state.restaurants.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                ToggleViewButton(
                    iconId = R.drawable.ic_baseline_location_on_24,
                    titleId = R.string.go_to_map_view,
                    onClicked = onMapViewButtonClick
                )
            }
        }
    }
}

@Preview
@Composable
fun RestaurantListScreenUILoading() {
    MaterialTheme {
        RestaurantListScreenUI(
            state = RestaurantListState(
                loading = true
            ),
            onMapViewButtonClick = { },
            onFavoritesClicked = {}
        )
    }
}

@Preview
@Composable
fun RestaurantListScreenUIData() {
    MaterialTheme {
        RestaurantListScreenUI(
            state = RestaurantListState(
                restaurants = (0..10).map {
                    Restaurant()
                }
            ),
            onMapViewButtonClick = { },
            onFavoritesClicked = { }
        )
    }
}

@Preview
@Composable
fun RestaurantListScreenUIError() {
    MaterialTheme {
        RestaurantListScreenUI(
            state = RestaurantListState(
                error = "Something went wrong, Please try again later."
            ),
            onMapViewButtonClick = { },
            onFavoritesClicked = { }
        )
    }
}

