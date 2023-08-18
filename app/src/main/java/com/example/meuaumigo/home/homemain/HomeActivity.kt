package com.example.meuaumigo.home.homemain

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        init()
    }

    private fun init(){
        navController().navigate(R.id.homeMainFragment)
        navigateSelector()
    }

    private fun navigateSelector(){
        binding.bnHome.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> navController().navigate(R.id.homeMainFragment)
                R.id.profile -> navController().navigate(R.id.homeProfileFragment)
                else -> {}
            }
            true
        }
    }

    private fun navController() = ((supportFragmentManager.findFragmentById(R.id.fcFragment)) as NavHostFragment).navController

}