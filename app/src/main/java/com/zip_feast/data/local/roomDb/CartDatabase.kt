package com.zip_feast.data.local.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zip_feast.data.local.models.CartItem
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(entities = [CartItem::class], version = 1,exportSchema = false)
@InstallIn(SingletonComponent::class)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}