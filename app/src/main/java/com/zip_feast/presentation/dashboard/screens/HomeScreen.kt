@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.zip_feast.presentation.dashboard.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zip_feast.R
import com.zip_feast.data.remote.models.productsModels.AllProductsResponseModel
import com.zip_feast.data.remote.models.productsModels.Data
import com.zip_feast.presentation.navigations.Routes
import com.zip_feast.presentation.products.Screens.AllProducts
import com.zip_feast.presentation.products.Screens.ErrorScreen
import com.zip_feast.presentation.products.viewmodel.ProductsViewModel
import com.zip_feast.presentation.theme.SkyBlue
import com.zip_feast.utils.apputils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { topAppBar() },
        containerColor = Color.White,

        ) { paddingValues ->
        val adjustedPadding = remember(paddingValues) {
            PaddingValues(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                top = paddingValues.calculateTopPadding() - 8.dp,
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding() - 16.dp
            )
        }
        Content(adjustedPadding, navController,viewModel)
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: ProductsViewModel
) {
    val productsResource by viewModel.products.observeAsState(Resource.Loading())
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Promotions()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            categoriesSection()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            flashSaleSection(productsResource,navController)
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(
                text = "All Products",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            )
        }
        item {
            AllProducts(productsResource,navController)
        }

    }
}

@Composable
fun categoriesSection() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Category",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "More Category..",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SkyBlue,
                modifier = Modifier.clickable {
                    // TODO: show all categories in seperate screen full
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CategoriesList()
    }
}

@Composable
fun Promotions() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 4
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = currentIndex) {
        delay(2000)
        currentIndex = (currentIndex + 1) % itemCount
        coroutineScope.launch {
            listState.animateScrollToItem(currentIndex)
        }
    }
    LazyRow(
        state = listState,
        modifier = Modifier.height(160.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PromotionsItem(
                title = "Shoes",
                subtitle = "start@",
                header = "MPR 1500",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.banner1),

                )
        }
        item {
            PromotionsItem(
                title = "Fresh Vegetables",
                subtitle = "Fresh",
                header = "MPR 100",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.vegs),
            )
        }
        item {
            PromotionsItem(
                title = "food",
                subtitle = "Offer",
                header = "MPR 554",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.banner4),
            )
        }
        item {
            PromotionsItem(
                title = "Fashion",
                subtitle = "Discount",
                header = "20%",
                backgroundColor = Color(color = 0xFFC2D8E8),
                imagePainter = painterResource(id = R.drawable.banner2),
            )
        }
        item {
            PromotionsItem(
                title = "food",
                subtitle = "Offer",
                header = "MPR 5",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.banner3),
            )
        }

        item {
            PromotionsItem(
                title = "Pizza",
                subtitle = "Just at",
                header = "MPR 540",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.pizza),
            )
        }
    }
}

@Composable
fun PromotionsItem(
    title: String = "",
    subtitle: String = "",
    header: String = "",
    backgroundColor: Color = Color.Transparent,
    imagePainter: Painter
) {
    Card(
        modifier = Modifier
            .width(366.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row {
//            Column(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxHeight(),
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = title,
//                    fontSize = 14.sp,
//                    style = MaterialTheme.typography.titleSmall,
//                    color = Color.White
//                )
//                Text(
//                    text = subtitle,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//                Text(
//                    text = header,
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
            Image(
                painter = imagePainter,
                contentDescription = "banner1",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    var searchText by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text(
                    text = "Search Food, grocery etc.",
                    fontSize = 12.sp,
                    color = uiColor,
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = SkyBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Color.Black
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun CategoriesList() {
    val iconsAndTitles = listOf(
        R.drawable.electronics_gadgets to "Electronics",
        R.drawable.dress to "Dress",
        R.drawable.health to "Health",
        R.drawable.pet_supplies to "Pet Supplies",
        R.drawable.restaurant to "Restaurant",
        R.drawable.school_bag to "School Bag",
        R.drawable.sports to "Sports",
        R.drawable.toys to "Toys",

        )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        items(iconsAndTitles) { iconResId ->
            IconCategories(
                iconResId = iconResId.first,
                title = iconResId.second,
                onClick = {
                    // TODO: handle here if user click any category navigate
                }
            )
        }
    }
}

@Composable
fun IconCategories(iconResId: Int, title: String, onClick: () -> Unit) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else SkyBlue
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(38.dp),
            painter = painterResource(id = iconResId),
            contentDescription = title,
            tint = uiColor
        )
        Text(
            text = title,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun flashSaleSection(productsResource: Resource<AllProductsResponseModel>, navController: NavHostController) {

    when (productsResource) {
        is Resource.Loading -> {
            //CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.2f))
        }
        is Resource.Success -> {
            val products = productsResource.data?.data ?: emptyList()
            FlashSaleItems(navController,products)
        }
        is Resource.Error -> {
            val errorMessage = productsResource.errorMessage
            ErrorScreen(errorMessage)
        }
    }

}

@Composable
private fun FlashSaleItems(
    navController: NavHostController,
    products: List<Data>
) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Flash Sale",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Text(
                text = "See More..",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SkyBlue,
                modifier = Modifier.clickable {
                    // TODO:  show here a screen where all product shown fully
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlashSaleList(products,navController)
    }
}

@Composable
fun FlashSaleList(products: List<Data>, navController: NavHostController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(products) { item ->
            FlashSaleCard(item = item) {
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
                    updatedAt = item.updatedAt
                )
                navController.navigate(Routes.ProductDetailScreen.createRoute(productDetail))
            }
        }
    }
}

@Composable
fun FlashSaleCard(item: Data, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .width(160.dp)
            .height(240.dp)
            .padding(horizontal = 8.dp),
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
                    .fillMaxWidth(),
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
                    text = "Rs "+item.price,
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