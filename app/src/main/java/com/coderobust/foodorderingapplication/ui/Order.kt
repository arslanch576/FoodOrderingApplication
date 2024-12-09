package com.coderobust.foodorderingapplication.ui

class Order{
    var id:String=""
    var customerId: String=""
    var customerName: String=""
    var customerAddress: String=""
    var total:Int=0
    var orderItems:List<OrderItem> = emptyList()
}