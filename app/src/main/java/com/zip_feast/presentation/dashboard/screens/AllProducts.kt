package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zip_feast.models.FlashSaleItem
import com.zip_feast.presentation.dashboard.dummyData.sampleProducts
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import com.zip_feast.presentation.theme.SkyBlue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AllProducts(navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        items(sampleProducts) { item ->
            AllProductsItems(item = item){
                val productDetail = ProductDetail(
                    imageResId = item.imageResId,
                    name = item.name,
                    price = item.price,
                    discount = item.discount,
                    rating = item.rating
                )
                val productJson = Json.encodeToString(productDetail)
                navController.navigate("productDetail/$productJson")
            }
        }
    }
}

@Composable
fun AllProductsItems(item: FlashSaleItem,onClick: ()-> Unit ){

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(240.dp)
            .padding(horizontal = 8.dp),
        onClick = {
                  onClick()
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            FeedbackStars(rating = item.rating)
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.price,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SkyBlue
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.discount,
                    fontSize = 10.sp,
                    color = Color.Red,
                )
            }
        }
    }

}