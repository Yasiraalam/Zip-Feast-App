@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.zip_feast.presentation.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zip_feast.R
import com.zip_feast.presentation.theme.SkyBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController) {
    Box {
        Scaffold(
            topBar = { topAppBar() },
            containerColor = Color.White,

            ) { paddingValues ->
            Content(paddingValues)
        }
    }
}

@Composable
fun Content(paddingValues: PaddingValues) {
    Column(Modifier.padding(paddingValues)) {
        Spacer(modifier = Modifier.height(20.dp))
        Promotions()
        Spacer(modifier = Modifier.height(20.dp))
        categoriesSection()
        Spacer(modifier = Modifier.height(20.dp))
        flashSaleSection()
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
                fontWeight = FontWeight.Normal,
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
    val itemCount = 4 // Number of items in the LazyRow
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
                header = "$15",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.banner1),

                )
        }
        item {
            PromotionsItem(
                title = "food",
                subtitle = "Offer",
                header = "$5",
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
                header = "$5",
                backgroundColor = Color(0xFF764DC7),
                imagePainter = painterResource(id = R.drawable.banner3),
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


@Composable
fun topAppBar() {
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
                Text(text = "Search Food, grocery etc.", fontSize = 12.sp)
            },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
        R.drawable.tshirt to "T-Shirt",
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(26.dp),
    ) {
        items(iconsAndTitles) { iconResId ->
            IconCategories(
                iconResId =iconResId.first,
                title =iconResId.second,
                onClick = {
                    // TODO: handle here if user click any category navigate
                }
            )
        }
    }
}
@Composable
fun IconCategories(iconResId: Int, title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(38.dp),
            painter = painterResource(id = iconResId),
            contentDescription = title,
            tint = Color(android.graphics.Color.parseColor("#40BFFF"))
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
fun flashSaleSection() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
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
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = SkyBlue,
                modifier = Modifier.clickable {
                    // TODO:  show here a screen where all product shown fully
                }
            )
        }
    }
}





