package com.coderobust.foodorderingapplication.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.coderobust.foodorderingapplication.model.repositories.CartItemRepository
import com.coderobust.foodorderingapplication.model.repositories.OrderRepository
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.Order
import com.coderobust.foodorderingapplication.ui.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(val cartItemRepository: CartItemRepository, val authRepository: AuthRepository, val orderRepository: OrderRepository):ViewModel() {
    val cartItems= MutableStateFlow<List<CartItem>>(emptyList())
    val total= MutableStateFlow<Int>(0)
    val failureMessage= MutableStateFlow<String?>(null)
    val isOrderPlaced= MutableStateFlow<Boolean?>(null)

    init {
        loadCartItems()
    }
    fun loadCartItems(){
        viewModelScope.launch {
            cartItemRepository.loadCartItems(authRepository.getCurrentUser()?.uid!!).collect{
                cartItems.value=it
                calculateTotal()
            }
        }
    }

    fun placeOrder(address:String){
        val orderItems=ArrayList<OrderItem>()
        for (item in cartItems.value){
            val orderItem=OrderItem()
            orderItem.id=item.id
            orderItem.name=item.foodItem!!.name
            orderItem.quantity=item.quantity
            orderItems.add(orderItem)
        }

        val order=Order()
        order.orderItems=orderItems
        order.customerAddress=address
        order.total=total.value
        val user=authRepository.getCurrentUser()
        order.customerId=user!!.uid!!
        order.customerName=user!!.displayName!!

        viewModelScope.launch {
            val result=orderRepository.saveOrder(order)
            if (result.isFailure) {
                failureMessage.value=result.exceptionOrNull()?.message
                return@launch
            }

            isOrderPlaced.value=true
            cartItemRepository.clearCart(authRepository.getCurrentUser()!!.uid)
        }


    }

    private fun calculateTotal(){
        var total=0
        for (item in cartItems.value){
            total+=item.foodItem!!.price*item.quantity
        }
        this.total.value=total
    }
}