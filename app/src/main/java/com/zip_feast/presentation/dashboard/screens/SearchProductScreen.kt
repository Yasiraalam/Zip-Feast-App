import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.remote.models.productsModels.Data
import com.zip_feast.presentation.dashboard.screens.ProductNotFound
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.products.viewmodel.ProductsViewModel
import com.zip_feast.utils.apputils.Resource

@Composable
fun SearchSuggestions(productsViewModel: ProductsViewModel, navController: NavHostController) {
    val searchResultsState by productsViewModel.filteredProducts.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        when (val searchResults = searchResultsState) {
            is Resource.Loading -> {
                item {
                    Text(text = "Loading...", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
            is Resource.Success -> {
                val products = searchResults.data ?: emptyList()
                    if (products.isEmpty()) {
                        item {
                            ProductNotFound()
                        }
                    } else {
                        items(products) { product ->
                            ProductCard(product, navController)
                        }
                    }
            }
            is Resource.Error -> {
                item {
                    ProductNotFound()
                    Text(text = "Error fetching products: ${searchResults.errorMessage}", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}
@Composable
fun ProductCard(
    product: Data,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(12.dp)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    Routes.ProductDetailScreen.createRoute(
                        product
                    )
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        ) {
        CartItem(product)
    }
}
@Composable
private fun CartItem(
    product: Data
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Gray
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.productImage),
            modifier = Modifier.size(90.dp),
            contentDescription = "item"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name,
                maxLines = 1,
                color = uiColor,
            )
            HorizontalDivider()
            Text(
                text = "Rs ${product.price}",
                color = Color.Blue,
                fontSize = 12.sp,
            )
            Text(
                text = if(product.isAvailable) "Available" else "Out of Stock",
                fontSize = 10.sp,
                color = if(product.isAvailable) Color.Green else Color.Red,
            )
        }
    }
}

