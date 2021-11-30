package com.example.warehouse.ui.epoxy.models

import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.databinding.ModelItemEntityBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel

data class ItemEntityEpoxyModel(
    val itemEntity: ItemEntity,
    val itemSelected: (String) -> Unit
) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

    override fun ModelItemEntityBinding.bind() {
        nameTextView.text = itemEntity.itemName
        countTextView.text = itemEntity.itemCount.toString()
        placeTextView.text = itemEntity.itemPlace

        root.setOnClickListener {
            itemSelected(itemEntity.itemId)
        }
    }
}