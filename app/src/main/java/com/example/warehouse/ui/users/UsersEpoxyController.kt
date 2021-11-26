package com.example.warehouse.ui.users

import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.database.entity.UserWithOrderEntity
import com.example.warehouse.databinding.ModelItemEntityBinding
import com.example.warehouse.databinding.ModelUserEntityBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel
import com.example.warehouse.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.warehouse.ui.epoxy.models.LoadingEpoxyModel
import com.example.warehouse.ui.items.ItemsViewModel

class UsersEpoxyController(
    private val onUserSelected: (String) -> Unit
): EpoxyController() {

    var usersViewState: UsersViewModel.UsersViewState = UsersViewModel.UsersViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (usersViewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (usersViewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        usersViewState.dataList.forEach { dataItem ->
            val userWithOrderEntity = dataItem.data as UserWithOrderEntity
            UserEntityEpoxyModel(userWithOrderEntity.userEntity, onUserSelected).id(userWithOrderEntity.userEntity.userId).addTo(this)
        }
    }

    data class UserEntityEpoxyModel(
        val userEntity: UserEntity,
        val userSelected: (String) -> Unit
    ) : ViewBindingKotlinModel<ModelUserEntityBinding>(R.layout.model_user_entity) {

        override fun ModelUserEntityBinding.bind() {
            fullNameTextView.text = userEntity.userFullName
            loginIdTextView.text = userEntity.userLoginId

            root.setOnClickListener {
                userSelected(userEntity.userId)
            }
        }
    }

}