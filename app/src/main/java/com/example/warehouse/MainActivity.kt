package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.warehouse.database.entity.UserEntity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView

    var currentUserId = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SharedPrefUtil.init(this)

        val viewModel: WarehouseViewModel by viewModels()
        viewModel.init(WarehouseDatabase.getDatabase(this))

        /*val userEntity = UserEntity(userId = "admin",
            userLoginId = "admin",
            userPassword = "admin",
            userFullName = "admin",
            UserPosition = "admin"
        )

        viewModel.insertUser(userEntity)*/

        //deleteDatabase("warehouse-database")

        //SharedPrefUtil.setCurrentUserId("none")

       /* if (SharedPrefUtil.getCurrentUserId() == "tom") {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            findNavController(fragment).navigate(R.id.loginFragment)
        }*/

        setupDrawerNavigation()

        //navigationView.setNavigationItemSelectedListener(NavigationViewListener(::onLogout))
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

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/
    private fun onLogout() {
        SharedPrefUtil.setSavedUserId("none")
        //navController.
    }

    private class NavigationViewListener(
        private val onLogout: () -> Unit
    ): NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.nav_logout -> {
                    onLogout()
                    false
                }
                else -> return false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}