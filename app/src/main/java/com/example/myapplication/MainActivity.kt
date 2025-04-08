package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MapKitFactory.setApiKey("0fe8444d-35c0-46cf-8c9e-a455ca3ee497")
        MapKitFactory.initialize(this)

        val api = "8a570e9a-f3a6-40a3-b6e8-b428579c9127"

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavId.setupWithNavController(navController)



    }
}