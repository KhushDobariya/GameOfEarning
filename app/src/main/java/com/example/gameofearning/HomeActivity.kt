package com.example.gameofearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var NavController = findNavController(R.id.nav_host_fragment)
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNav.setupWithNavController(NavController)
    }
}