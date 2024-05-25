package com.zip_feast.ui.auth.screens

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zip_feast.R
import com.zip_feast.ui.theme.LightBlueWhite
import com.zip_feast.ui.theme.blueGray

@Composable
fun SocialMediaLogin(
    @DrawableRes icon:Int,
    modifier: Modifier = Modifier,
    text:String,
    onclick:()->Unit
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .socialMedia()
            .clickable { onclick() }
            .height(40.dp)
            .background(color = Color(0xFFE9ECF3)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier=Modifier
            .size(30.dp),
            painter = painterResource(id = icon),
            contentDescription = "google icon"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(color = Color(0xEB1839B3))
        )

    }

}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.socialMedia():Modifier =composed {
    if (isSystemInDarkTheme()){
        background(Color.Transparent).border(
            width = 1.dp,
            color = blueGray,
            shape = RoundedCornerShape(4.dp)
        )
    }else{
        background(LightBlueWhite)
    }
}