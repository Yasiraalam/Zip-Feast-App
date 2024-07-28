package com.zip_feast.presentation.products.Screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.remote.models.AllProductsResponseModel
import com.zip_feast.data.remote.models.Data
import com.zip_feast.presentation.dashboard.navigations.Routes
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource

@Composable
fun AllProducts(productsResource: Resource<AllProductsResponseModel>, navController: NavHostController) {

    Column(modifier = Modifier.fillMaxSize()) {
        when (productsResource) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                val products = (productsResource as Resource.Success<AllProductsResponseModel>).data?.data ?: emptyList()
                ProductsList(navController,products)
            }
            is Resource.Error -> {
                val errorMessage = (productsResource as Resource.Error).errorMessage
                ErrorScreen(errorMessage)
            }
        }
    }
}

@Composable
private fun ProductsList(
    navController: NavHostController,
    products: List<Data>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        items(products) { item ->
            AllProductsItems(item = item) {
                val productDetail = Data(
                    category = item.category,
                    createdAt = item.createdAt,
                    description = item.description,
                    id = item.id,
                    isAvailable = item.isAvailable,
                    merchant = item.merchant,
                    merchantId = item.merchantId,
                    name = item.name,
                    price = item.price,
                    productImage = item.productImage,
                    stock = item.stock,
                    updatedAt = item.updatedAt,
                )
                navController.navigate(Routes.ProductDetailScreen.createRoute(productDetail))
            }
        }
    }
}

@Composable
fun AllProductsItems(item: Data,onClick: ()-> Unit ){

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
                    painter = rememberAsyncImagePainter(model = item.productImage),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₨ "+item.price,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SkyBlue
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if(item.isAvailable) "Available" else "Not Available",
                    fontSize = 10.sp,
                    color = if(item.isAvailable) Color.Green else Color.Red,
                )
            }
        }
    }


}
@Composable
fun ErrorScreen(errorMessage: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = errorMessage ?: "An unknown error occurred")
        Button(onClick = { /* Retry logic */ }) {
            Text(text = "Retry")
        }
    }
}