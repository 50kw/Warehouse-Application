package com.example.warehouse


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.warehouse.arch.WarehouseViewModel
import com.example.warehouse.database.WarehouseDatabase
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navigationView: NavigationView

    val warehouseViewModel: WarehouseViewModel by viewModels()

    var currentUserId = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SharedPrefUtil.init(this)

        warehouseViewModel.init(WarehouseDatabase.getDatabase(this))

        setupDrawerNavigation()

    }

    private fun setupDrawerNavigation() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_orders,
            R.id.nav_items,
            R.id.nav_users,
        ), drawerLayout)

        navigationView = findViewById(R.id.nav_view)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    /*private fun onLogout() {
        SharedPrefUtil.setSavedUserId("none")
        SharedPrefUtil.setCurrentUserId("none")
        currentUserId = "none"
        navController.navigate(R.id.nav_orders)
        drawerLayout.closeDrawers()
        setDrawerLocked(true)
    }

    fun setDrawerLocked(locked: Boolean) {
        val lockMode = if (locked) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED
        drawerLayout.setDrawerLockMode(lockMode)
    }

    private class NavigationViewListener(
        private val onLogout: () -> Unit
    ): NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                *//*R.id.nav_logout -> {
                    onLogout()
                    false
                }*//*
                else -> return false
            }
        }

    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}