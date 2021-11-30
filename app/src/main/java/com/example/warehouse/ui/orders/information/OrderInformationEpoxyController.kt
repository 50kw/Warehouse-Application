package com.example.warehouse.ui.orders.information;

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.Constants
import com.example.warehouse.R
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import com.example.warehouse.databinding.ModelOrderInformationBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel
import com.example.warehouse.ui.epoxy.models.*

class OrderInformationEpoxyController(
    private val orderInformationInterface: OrderInformationInterface
) : EpoxyController(){

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var orderWithItemEntities: OrderWithItemEntities = OrderWithItemEntities(OrderEntity(), emptyList())
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (orderWithItemEntities.orderEntity.orderId == "") {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        HeaderEpoxyModel("Order Information").id("order_information_header").addTo(this)

        OrderEntityInformationEpoxyModel(orderWithItemEntities.orderEntity, orderInformationInterface).id("order_information").addTo(this)

        HeaderEpoxyModel("Order Items").id("order_items_header").addTo(this)

        orderWithItemEntities.ItemEntities.forEach { itemEntity ->
            ItemEntityEpoxyModel(itemEntity, orderInformationInterface::onItemSelected).id(itemEntity.itemId).addTo(this)
        }

    }

    data class OrderEntityInformationEpoxyModel(
        val orderEntity: OrderEntity,
        val orderInformationInterface: OrderInformationInterface
    ) : ViewBindingKotlinModel<ModelOrderInformationBinding>(R.layout.model_order_information) {

        override fun ModelOrderInformationBinding.bind() {
            nameTextView.text = orderEntity.orderName
            descriptionTextView.text = orderEntity.orderDescription
            statusTextView.text = orderEntity.orderStatus
            responsibleUserTextView.text = orderEntity.userOwnerId

            val colorId = when (orderEntity.orderStatus) {
                Constants.ORDER_PENDING -> android.R.color.holo_red_light
                Constants.ORDER_IN_PROGRESS -> android.R.color.holo_orange_dark
                Constants.ORDER_COMPLETED -> android.R.color.holo_green_dark
                else -> android.R.color.holo_blue_dark
            }

            val color = ContextCompat.getColor(root.context, colorId)
            statusTextView.setTextColor(color)

            selectButton.setOnClickListener {
                if (orderEntity.userOwnerId.isEmpty()) {
                    selectButton.isCheckable = true

                    orderInformationInterface.onSelectUserClicked(orderEntity.orderId)
                } else {
                    selectButton.isCheckable = false
                }
            }

            addItemButton.setOnClickListener {
                orderInformationInterface.onAddItemsClicked(orderEntity.orderId)
            }
        }
    }

}
