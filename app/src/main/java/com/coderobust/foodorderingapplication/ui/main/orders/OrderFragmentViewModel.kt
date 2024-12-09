package com.coderobust.foodorderingapplication.ui.main.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.coderobust.foodorderingapplication.model.repositories.OrderRepository
import com.coderobust.foodorderingapplication.ui.Order
import com.coderobust.foodorderingapplication.ui.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderFragmentViewModel @Inject constructor( val authRepository: AuthRepository, val orderRepository: OrderRepository):ViewModel() {
    val orders= MutableStateFlow<List<Order>>(emptyList())
    val failureMessage= MutableStateFlow<String?>(null)

    init {
        loadOrders()
    }
    fun loadOrders(){
        viewModelScope.launch {
            orderRepository.loadOrders(authRepository.getCurrentUser()?.uid!!).collect{
                orders.value=it
            }
        }
    }

}