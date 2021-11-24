package com.example.warehouse.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.example.warehouse.MainActivity
import com.example.warehouse.arch.WarehouseViewModel
import com.example.warehouse.database.WarehouseDatabase

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val warehouseDatabase: WarehouseDatabase
        get() = WarehouseDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: WarehouseViewModel by activityViewModels()


    protected fun navigateViaNavGraph(actionId: Int) {
        mainActivity.navController.navigate(actionId)
    }

    protected fun navigateViaNavGraph(navDirections: NavDirections) {
        mainActivity.navController.navigate(navDirections)
    }

    protected fun navigateUp() {
        mainActivity.navController.navigateUp()
    }
}