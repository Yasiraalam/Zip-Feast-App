package com.zip_feast.data.local.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zip_feast.data.local.models.CartItem
import com.zip_feast.utils.converters.Converters
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(entities = [CartItem::class], version = 2,exportSchema = false)
@TypeConverters(Converters::class)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}