package com.zip_feast.presentation.dashboard.dummyData

import com.zip_feast.R
import com.zip_feast.models.FlashSaleItem

val sampleFlashSaleItems = listOf(
    FlashSaleItem(productId =1, R.drawable.shoes1, name = "Reebok Classic", price = "MRP 12,599", discount = "30 Off",rating = 4),
    FlashSaleItem(productId =2, R.drawable.shoes2, name = "Asics Gel-Kayano", price = "MRP 1159", discount = "12 Off",rating = 2),
    FlashSaleItem(productId =3, R.drawable.shoes3, name = "Reebok Classic", price = "MRP 860", discount = "40 Off",rating = 3),
    FlashSaleItem(productId =4, R.drawable.n1, name = "Nike Air Max", price = "MRP 12,099", discount = "10 Off",rating = 4),
    FlashSaleItem(productId =5, R.drawable.n2, name = "Adidas Ultraboost", price = "MRP 13,050", discount = "15 Off",rating = 3),
    FlashSaleItem(productId =6, R.drawable.n3, name = "Puma RS-X", price = "MRP 2250", discount = "25 Off",rating = 5),
)

val sampleMegaSaleItems = listOf(
    FlashSaleItem(productId =6, R.drawable.n1, name = "Nike Air Max", price = "MRP 12,099", discount = "10% Off",rating = 1),
    FlashSaleItem(productId =7, R.drawable.n2, name = "Adidas Ultraboost", price = "MRP 13,034", discount = "15% Off",rating = 2),
    FlashSaleItem(productId =8, R.drawable.n3, name = "Puma RS-X", price = "MRP 2255", discount = "25% Off",rating = 5),
    FlashSaleItem(productId =9, R.drawable.shoes1, name = "Reebok Classic", price = "MRP 860", discount = "30% Off",rating = 4),
)
val sampleProducts = listOf(
    FlashSaleItem(productId =10,R.drawable.dress1, name = "Dress1", price = "MPR 1200", discount = "10% Off",rating = 4),
    FlashSaleItem(productId =11, R.drawable.dress2, name = "Dress2", price = "MPR 1400", discount = "10% Off",rating = 5),
    FlashSaleItem(productId =12, R.drawable.dress3, name = "Dress3", price = "MPR 2050", discount = "10% Off",rating = 3),
    FlashSaleItem(productId =13, R.drawable.tshirt2, name = "Girls T-Shirt2", price = "MPR 520", discount = "10% Off",rating = 4),
    FlashSaleItem(productId =14, R.drawable.shoes2, name = "Asics Gel-Kayano", price = "MPR 615", discount = "12% Off",rating = 2),
    FlashSaleItem(productId =15, R.drawable.shoes3, name = "Reebok Classic", price = "MPR 860", discount = "40% Off",rating = 3)
)