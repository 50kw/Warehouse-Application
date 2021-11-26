package com.example.warehouse.ui.orders

import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.R
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import com.example.warehouse.databinding.ModelOrderEntityBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel
import com.example.warehouse.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.warehouse.ui.epoxy.models.LoadingEpoxyModel

class OrderEpoxyController(
    private val onOrderSelected: (String) -> Unit
): EpoxyController() {

    var ordersViewState: OrderViewModel.OrdersViewState = OrderViewModel.OrdersViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (ordersViewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (ordersViewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        ordersViewState.dataList.forEach { dataItem ->
            val orderWithItemEntities = dataItem.data as OrderWithItemEntities
            OrderEntityEpoxyModel(orderWithItemEntities.orderEntity, onOrderSelected).id(orderWithItemEntities.orderEntity.orderId).addTo(this)
        }
    }

    data class OrderEntityEpoxyModel(
        val orderEntity: OrderEntity,
        val orderSelected: (String) -> Unit
    ) : ViewBindingKotlinModel<ModelOrderEntityBinding>(R.layout.model_order_entity) {

        override fun ModelOrderEntityBinding.bind() {
            orderNameTextView.text = orderEntity.orderName
            orderStatusTextView.text = orderEntity.orderStatus

            val color = when (orderEntity.orderStatus) {
                "Pending" -> android.R.color.holo_red_light
                "In Progress" -> android.R.color.holo_orange_dark
                "Completed" -> android.R.color.holo_green_dark
                else -> android.R.color.holo_blue_dark
            }

            orderStatusTextView.setTextColor(color)

            root.setOnClickListener {
                orderSelected(orderEntity.orderId)
            }
        }
    }
}