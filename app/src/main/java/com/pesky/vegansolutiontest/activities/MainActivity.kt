package com.pesky.vegansolutiontest.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pesky.vegansolutiontest.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.main_activity_nav_host)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_activity_nav_host)
        return navController.navigateUp()
    }
}