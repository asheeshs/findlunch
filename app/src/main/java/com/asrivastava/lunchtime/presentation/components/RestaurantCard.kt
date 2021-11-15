package com.asrivastava.lunchtime.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asrivastava.lunchtime.R
import com.asrivastava.lunchtime.domain.model.Restaurant

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    showSaved: Boolean = true,
    onSavedClicked: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Top
        ) {
            Image(
                painterResource(id = R.drawable.ic_martis_trail_as_default),
                contentDescription = "",
                modifier = Modifier
                    .align(CenterVertically)
                    .size(75.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = restaurant.name,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.subtitle1
                )
                RatingView(
                    rating = restaurant.rating,
                    totalUserRatings = restaurant.totalUserRatings
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val dollars = restaurant.priceLevel?.let {
                        (0..it).joinToString("") { "$" } +  " · "
                    } ?: ""
                    val thirdLine = "$dollars${
                        restaurant.businessStatus.lowercase()
                            .replaceFirstChar { ch -> ch.uppercase() }
                    }"
                    Text(
                        text = thirdLine,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

            }
            if (showSaved) {
                FavoriteView(
                    isFavorite = restaurant.favorite
                ) {
                    onSavedClicked()
                }
            }
        }
    }
}

@Composable
private fun ImageContainer(content: @Composable () -> Unit) {
    Surface(Modifier.size(width = 60.dp, height = 60.dp), RoundedCornerShape(4.dp)) {
        content()
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        RestaurantCard(restaurant = Restaurant(priceLevel = 5)) {}
    }
}

