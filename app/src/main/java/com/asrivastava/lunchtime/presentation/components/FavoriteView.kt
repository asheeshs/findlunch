package com.asrivastava.lunchtime.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asrivastava.lunchtime.R

@Composable
fun FavoriteView(
    isFavorite: Boolean,
    onSavedClicked: () -> Unit
) {
    val id = if (isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
    Icon(
        painter = painterResource(id = id),
        contentDescription = "",
        modifier = Modifier
            .size(30.dp)
            .clickable { onSavedClicked() },
        tint = if (isFavorite) Color(0xFF55872D) else Color(0xFFDFDFDF)
    )
}

@Preview
@Composable
fun PreviewFavoriteView() {
    MaterialTheme {
        Column {
            FavoriteView(isFavorite = true) {

            }
            FavoriteView(isFavorite = false) {

            }
        }

    }
}
