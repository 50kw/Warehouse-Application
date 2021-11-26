package com.example.warehouse.ui

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.example.warehouse.MainActivity
import com.example.warehouse.arch.WarehouseViewModel
import com.example.warehouse.database.WarehouseDatabase

import android.widget.TextView
import com.example.warehouse.R


abstract class BaseFragment : Fragment() {

    protected fun getCurrentUser(): String {
        return mainActivity.currentUserId
    }

    protected fun setCurrentUser(userId: String) {
        mainActivity.currentUserId = userId
    }

    protected fun setDrawerLocked(locked: Boolean) {
        val lockMode = if (locked) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED
        mainActivity.drawerLayout.setDrawerLockMode(lockMode)
    }

   protected fun setNavigationHeaderUserId(userId: String) {
        //mainActivity.navigationView.inflateHeaderView(R.layout.navigation_header)
        val headerView = mainActivity.navigationView.getHeaderView(0)
        val headerUserId : TextView = headerView.findViewById(R.id.navHeaderUserIdTextView)
        headerUserId.text = userId
    }

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