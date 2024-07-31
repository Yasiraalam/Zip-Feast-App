package com.zip_feast.presentation.dashboard.screens

import ProductCard
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.remote.models.productsModels.Data
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.products.viewmodel.ProductsViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource

@Composable
fun SearchResultScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val query = navController.currentBackStackEntry?.arguments?.getString("query")
//    LaunchedEffect(key1 = query) {
//        query?.let {
//            Log.d("yasiralamchaniza", "query in launched effect : $query")
//            productsViewModel.searchedProduct(it)
//        }
//    }
    val searchResults by productsViewModel.searchedProduct.collectAsState()
    when (searchResults) {
        is Resource.Loading -> {
            CircularProgressIndicator()
        }
        is Resource.Success -> {
            val products = (searchResults as Resource.Success<List<Data>>).data ?: emptyList()
            if (products.isEmpty()) {
                Text(
                    text = "Product not found",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    items(products) { product ->
                        ProductCard(product, navController)
                    }
                }
            }
        }
        is Resource.Error -> {
            ProductNotFound()
        }

    }
}
