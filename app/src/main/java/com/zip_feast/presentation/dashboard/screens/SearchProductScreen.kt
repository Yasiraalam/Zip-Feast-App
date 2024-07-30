import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zip_feast.presentation.products.viewmodel.ProductsViewModel

@Composable
fun SearchSuggestions(productsViewModel: ProductsViewModel, navController: NavHostController) {
    val searchResults by productsViewModel.filteredProducts.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        items(searchResults) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("product_detail_screen/${product.id}")
                    }
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}