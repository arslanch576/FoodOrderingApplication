package com.coderobust.foodorderingapplication.model.repositories

import com.coderobust.foodorderingapplication.ui.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepository @Inject constructor() {
    val orderCollection=FirebaseFirestore.getInstance().collection("orders")

    suspend fun saveOrder(order: Order):Result<Boolean> {
        try {
            val document=orderCollection.document()
            order.id=document.id
            document.set(order).await()
            return Result.success(true)
        }catch (e:Exception){
            return Result.failure(e)
        }
    }
    suspend fun updateOrder(order: Order):Result<Boolean> {
        try {
            val document=orderCollection.document(order.id)
            document.set(order).await()
            return Result.success(true)
        }catch (e:Exception){
            return Result.failure(e)
        }
    }

    fun loadOrders(userId:String)=orderCollection.whereEqualTo("customerId",userId).snapshots().map { it.toObjects(Order::class.java) }
}