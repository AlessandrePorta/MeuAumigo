package com.example.meuaumigo.home.homemain

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        supportFragmentManager.beginTransaction().replace(R.id.fcFragment, HomeMainFragment.newInstance()).commit()
        navigateSelector()
    }

    private fun navigateSelector(){
        binding.bnHome.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeMainFragment.newInstance())
                R.id.profile -> replaceFragment(HomeProfileFragment.newInstance())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcFragment, fragment)
        fragmentTransaction.commit()
    }

}