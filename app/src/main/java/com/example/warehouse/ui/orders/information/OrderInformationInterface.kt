package com.example.warehouse.ui.orders.information

import com.example.warehouse.database.entity.OrderEntity

interface OrderInformationInterface {
    fun onSelectUserClicked(orderId: String)
    fun onItemSelected(itemId: String)
    fun onAddItemsClicked(orderId: String)
}