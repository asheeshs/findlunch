package com.asrivastava.lunchtime.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asrivastava.lunchtime.R

@Composable
fun ToggleViewButton(
    @DrawableRes iconId: Int,
    @StringRes titleId: Int,
    onClicked: () -> Unit,
) {
    Button(
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF519B9A),
            contentColor = Color.White
        ),
        modifier = Modifier.width(width = 100.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                modifier = Modifier.size(16.dp)

            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = stringResource(titleId),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview
@Composable
fun PreviewToggleViewButton() {
    MaterialTheme {
        ToggleViewButton(
            R.drawable.ic_baseline_location_on_24,
            R.string.go_to_map_view
        ) {

        }
    }
}
