import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.data.local.models.CartItem
import com.zip_feast.data.remote.models.productsModels.Data
import com.zip_feast.presentation.cart.cartViewmodel.CartViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.CustomSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    product: Data,
    cartViewModel: CartViewModel = hiltViewModel<CartViewModel>(),
    onBackClick: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { ProductTopAppBar(product.name, onBackClick) },
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 56.dp)
            ) { data ->
                CustomSnackBar(snackbarData = data)
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                ProductDetail(product, cartViewModel, navController = navController,snackbarHostState)
            }
        }
    }
}

@Composable
fun ProductTopAppBar(productName: String, onBackClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 5.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = Icons.Outlined.ArrowBack,
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .clickable { onBackClick() },
            contentDescription = "back button"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = productName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(end = 64.dp)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "MoreVert",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProductDetail(
    product: Data,
    cartViewModel: CartViewModel,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.productImage),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(12 / 10f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.name,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rs " + product.price,
            fontSize = 16.sp,
            color = SkyBlue,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = product.description,
                fontSize = 11.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = if (product.isAvailable) "Available" else "Not Available",
                fontSize = 10.sp,
                color = if (product.isAvailable) Color.Green else Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
        Text(
            text = "Stock: "+product.stock,
            fontSize = 11.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    val cartItem = CartItem(
                        category = product.category,
                        createdAt = product.createdAt,
                        description = product.description,
                        id = product.id,
                        isAvailable = product.isAvailable,
                        merchant = product.merchant,
                        merchantId = product.merchantId,
                        name = product.name,
                        price = product.price,
                        productImage = product.productImage,
                        stock = product.stock,
                        updatedAt = product.updatedAt,
                        quantity = 1
                    )
                    cartViewModel.insert(cartItem)
                    snackbarHostState.showSnackbar(
                        message = "${product.name} added to cart",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Add to Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
//                val productsId = UserOrderModel(
//                    productId = product.id,
//                    quantity = 1
//                )
//                navController.navigate(
//                    Routes.ShippingDetailsScreen.sendToShip(productsId)
//                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Buy Now",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
    }
}


