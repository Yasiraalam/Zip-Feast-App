package com.zip_feast.presentation.cart.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.R
import com.zip_feast.data.local.models.CartItem
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.UserOrderModel
import com.zip_feast.data.remote.models.ordersModels.orderRequestModels.CartOrderRequestModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.presentation.cart.cartViewmodel.CartViewModel
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.utils.apputils.CustomSnackBar
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel<CartViewModel>(),
    navController: NavHostController
) {
    val cartItems by viewModel.allCartItems.observeAsState(emptyList())
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 56.dp)
            ) { data ->
                CustomSnackBar(snackbarData = data)
            }
        },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Your Cart",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp),
                    color = uiColor
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = uiColor
                    )
                }
            }
        },
    ) { innerPadding ->
        val innerPadding = innerPadding

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CartItemsSection(
                cartItems,
                innerPadding,
                navController = navController,
                viewModel,
                onRemoveItem = { viewModel.delete(it) },
                snackbarHostState
            )
        }
    }
}

@Composable
fun CartItemsSection(
    cartItems: List<CartItem>,
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: CartViewModel,
    onRemoveItem: (CartItem) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val totalQuantity by viewModel.totalQuantity.observeAsState(0)
    val totalPrice by viewModel.totalPrice.observeAsState(0.0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(cartItems) { cartItem ->
            CartItemCard(cartItem, viewModel, navController = navController, onRemoveItem)
        }
        item {
            CouponSection(snackbarHostState)
            Spacer(modifier = Modifier.height(10.dp))
            ShippingItemsSection(totalQuantity,totalPrice)
        }
        item {
            CheckOutButton(cartItems,viewModel,navController,totalPrice,totalQuantity,snackbarHostState)
        }

    }
}

@Composable
fun CheckOutButton(
    cartItems: List<CartItem>,
    viewModel: CartViewModel,
    navController: NavHostController,
    totalPrice: Double,
    totalQuantity: Int,
    snackbarHostState: SnackbarHostState
) {
    Button(
        onClick = {
            val userOrderModels = cartItems.map {
                UserOrderModel(
                    productId = it.id,
                    quantity = it.quantity
                )
            }
            val cartOrderRequestModel = CartOrderRequestModel(
                cart = userOrderModels,
                deliveryAddress = "",
                paymentMethod = ""
            )
            navController.navigate(Routes.ShippingDetailsScreen.sendToShip(cartOrderRequestModel))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SkyBlue,
            contentColor = Color.White
        ),
    ) {
        Text(
            text = "Check Out",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
fun ShippingItemsSection(totalQuantity: Int, totalPrice: Double) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Items(${totalQuantity})", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs $totalPrice", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Shipping", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs ${40}", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Import Charges", fontSize = 10.sp, color = Color.Gray)
                Text(text = "Rs ${30}", fontSize = 10.sp,fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Price", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Rs: $totalPrice", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SkyBlue)

            }
        }

    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    viewModel: CartViewModel,
    navController: NavHostController,
    onRemoveItem: (CartItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(12.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        CartItem(cartItem, viewModel, navController = navController,onRemoveItem)
    }
    HorizontalDivider()
}

@Composable
fun CouponSection(snackbarHostState: SnackbarHostState) {
    var couponCode by remember { mutableStateOf("") }
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Gray
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(55.dp),
            maxLines = 1,
            value = couponCode,
            onValueChange = { couponCode = it },
            label = { Text("Coupon Code", fontSize = 14.sp, color = uiColor) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "No Coupon is Available Now ",
                        actionLabel = "Ok",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            modifier = Modifier
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Apply",
                fontSize = 12.sp,
                fontStyle = FontStyle.Normal,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CartItem(
    cartItem: CartItem,
    viewModel: CartViewModel,
    navController:NavHostController,
    onRemoveItem: (CartItem) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = cartItem.productImage),
            modifier = Modifier.size(90.dp),
            contentDescription = "item"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartItem.name,
                maxLines = 1,
                color = uiColor,
            )
            HorizontalDivider()
            Text(
                text = "Rs ${cartItem.price}",
                color = SkyBlue,
                fontSize = 12.sp,
            )
            if (cartItem.quantity >= cartItem.stock.toInt()) {
                Text(
                    text = "Only ${cartItem.stock} left ",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }else{
                Text(
                    text = if(cartItem.isAvailable) "Available" else "Out of Stock",
                    fontSize = 10.sp,
                    color = if(cartItem.isAvailable) Color.Green else Color.Red,
                )
            }

        }
        Column(
            modifier = Modifier.width(120.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = { onRemoveItem(cartItem) }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = uiColor
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { viewModel.decreaseQuantity(cartItem) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "minus icon",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SkyBlue)
                    )
                }
                Text(
                    text = "${cartItem.quantity}",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                IconButton(onClick = { viewModel.increaseQuantity(cartItem) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "plus icon",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SkyBlue)
                    )
                }
            }
        }
    }
}
