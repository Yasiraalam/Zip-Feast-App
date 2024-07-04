
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zip_feast.data.local.models.CartItem
import com.zip_feast.presentation.dashboard.navigations.navmodel.ProductDetail
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.presentation.cart.cartViewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    cartViewModel: CartViewModel = hiltViewModel<CartViewModel>(),
    product: ProductDetail,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { ProductTopAppBar(product.name, onBackClick) },
        containerColor = Color.White
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ProductDetail(product, cartViewModel)
        }
    }
}

@Composable
fun ProductTopAppBar(productName: String, onBackClick: () -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(top = 50.dp, start = 10.dp, end = 5.dp)
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
            color = Color.Black
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .fillMaxSize(0.9f),
                tint = Color.Black
            )
        }
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
fun ProductDetail(product: ProductDetail, cartViewModel: CartViewModel) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(16 / 9f)
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
            text = "product.price",
            fontSize = 16.sp,
            color = SkyBlue,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = product.discount,
            fontSize = 14.sp,
            color = Color.Red,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    val cartItem = CartItem(
                        productId = product.productId,
                        imageResId = product.imageResId,
                        name = product.name,
                        price = product.price,
                        discount = product.discount,
                        rating = product.rating,
                        quantity = 1,
                    )
                    cartViewModel.insert(cartItem)
                    Toast.makeText(context, "Item added in Cart", Toast.LENGTH_SHORT).show()
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
            onClick = { /*TODO*/ },
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
                fontStyle = FontStyle.Normal,
            )
        }
    }
}

