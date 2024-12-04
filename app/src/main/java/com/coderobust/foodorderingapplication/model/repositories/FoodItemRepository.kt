package com.coderobust.foodorderingapplication.model.repositories

import com.coderobust.foodorderingapplication.ui.FoodItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FoodItemRepository @Inject constructor() {
    val foodItemCollection=FirebaseFirestore.getInstance().collection("foodItems")

    suspend fun saveFoodItem(foodItem: FoodItem):Result<Boolean> {
        try {
            val document=foodItemCollection.document()
            foodItem.id=document.id
            document.set(foodItem).await()
            return Result.success(true)
        }catch (e:Exception){
            return Result.failure(e)
        }
    }

    fun loadFoodItems()=foodItemCollection.snapshots().map { it.toObjects(FoodItem::class.java) }
}