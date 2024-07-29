package com.zip_feast.utils.converters

import androidx.room.TypeConverter
import com.zip_feast.data.remote.models.productsModels.Merchant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromMerchant(value: Merchant): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toMerchant(value: String): Merchant {
        return Json.decodeFromString(value)
    }
}

