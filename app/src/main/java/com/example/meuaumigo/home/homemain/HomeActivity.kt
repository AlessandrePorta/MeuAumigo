package com.example.meuaumigo.home.homemain

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.ActivityMainBinding
import com.example.meuaumigo.viewmodel.FirebaseStorageViewModel
import com.google.firebase.FirebaseApp

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var viewModel : FirebaseStorageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseApp.initializeApp(this)
        init()
    }

    private fun init(){
        navController().navigate(R.id.homeMainFragment)
        navigateSelector()

        viewModel = ViewModelProviders.of(this)[FirebaseStorageViewModel::class.java]
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

    fun setNavigateSelectorInvisible(){
        binding.bnHome.visibility = View.GONE
    }

    fun setNavigateSelectorVisible(){
        binding.bnHome.visibility = View.VISIBLE
    }

    private fun navController() = ((supportFragmentManager.findFragmentById(R.id.fcFragment)) as NavHostFragment).navController

}