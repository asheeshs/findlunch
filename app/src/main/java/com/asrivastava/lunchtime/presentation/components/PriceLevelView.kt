package com.asrivastava.lunchtime.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun PriceLevelView(
    priceLevel: Int?,
    modifier: Modifier = Modifier
) {
    priceLevel?.let {
        Row {
            repeat((1..it).count()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_attach_money_24),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPriceView() {
    MaterialTheme {
        Column {
            PriceLevelView(5)
            PriceLevelView(4)
            PriceLevelView(3)
            PriceLevelView(2)
            PriceLevelView(1)
            PriceLevelView(null)
        }
    }
}
