package com.zip_feast.di

import android.content.Context
import androidx.room.Room
import com.zip_feast.data.local.roomDb.CartDatabase
import com.zip_feast.data.local.roomDb.CartItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }

    @Provides
    fun provideCartItemDao(database: CartDatabase): CartItemDao {
        return database.cartItemDao()
    }
}