package com.asrivastava.lunchtime.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asrivastava.lunchtime.R

@Composable
fun RatingView(
    rating: Double,
    totalUserRatings: Int,
    modifier: Modifier = Modifier
) {
    val colorHighlighted = Color(0xFFEBD261)
    val colorGrayed = Color(0xFFE5E5E5)
    val tint1 = if (rating >= 1) colorHighlighted else colorGrayed
    val tint2 = if (rating >= 2) colorHighlighted else colorGrayed
    val tint3 = if (rating >= 3) colorHighlighted else colorGrayed
    val tint4 = if (rating >= 4) colorHighlighted else colorGrayed
    val tint5 = if (rating == 5.0) colorHighlighted else colorGrayed

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24),
            contentDescription = "",
            tint = tint1
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24),
            contentDescription = "",
            tint = tint2
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24),
            contentDescription = "",
            tint = tint3
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24),
            contentDescription = "",
            tint = tint4
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24),
            contentDescription = "",
            tint = tint5
        )

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            text = "($totalUserRatings)",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewRatingView() {
    MaterialTheme {
        Column {
            (0..5).forEach {
                RatingView(rating = it.toDouble(), totalUserRatings = 5)
                Spacer(modifier = Modifier.size(6.dp))
            }
            RatingView(rating = 1.5, totalUserRatings = 236)
        }
    }
}
