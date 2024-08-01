package com.zip_feast.utils.extensionFunctions

import com.zip_feast.data.local.models.CartItem
import com.zip_feast.data.remote.models.ordersModels.UserOrderModel

fun CartItem.toUserOrderModel(): UserOrderModel {
    return UserOrderModel(
        productId = this.id,
        quantity = this.quantity
    )
}
fun mapCartItemToUserOrderModel(cartItem: CartItem): UserOrderModel {
    return UserOrderModel(
        productId = cartItem.id,
        quantity = cartItem.quantity
    )
}