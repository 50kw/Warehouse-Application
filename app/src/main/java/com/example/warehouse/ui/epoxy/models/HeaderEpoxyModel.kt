package com.example.warehouse.ui.epoxy.models;

import com.example.warehouse.R
import com.example.warehouse.databinding.ModelHeaderItemBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel

data class HeaderEpoxyModel(
    val headerText: String
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {

    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}