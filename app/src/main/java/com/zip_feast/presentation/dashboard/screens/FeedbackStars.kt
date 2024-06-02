package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zip_feast.R

@Composable
fun FeedbackStars(rating: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(rating) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Filled Star",
                modifier = Modifier.size(15.dp)
            )

        }
        repeat(5 - rating) {
            Image(
                painter = painterResource(id = R.drawable.hstar),
                contentDescription = "Empty Star",
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

