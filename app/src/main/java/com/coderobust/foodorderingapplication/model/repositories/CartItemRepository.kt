package com.coderobust.foodorderingapplication.model.repositories

import com.coderobust.foodorderingapplication.ui.CartItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CartItemRepository {
    val cartItemCollection=FirebaseFirestore.getInstance().collection("cartItems")

    suspend fun saveCartItem(cartItem: CartItem):Result<Boolean> {
        try {
            val document=cartItemCollection.document()
            cartItem.id=document.id
            document.set(cartItem).await()
            return Result.success(true)
        }catch (e:Exception){
            return Result.failure(e)
        }
    }

    fun loadCartItems(userId:String)=cartItemCollection.whereEqualTo("userId",userId).snapshots().map { it.toObjects(CartItem::class.java) }
}