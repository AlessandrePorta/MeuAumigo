package com.example.meuaumigo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meuaumigo.databinding.FragmentProfileBinding
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth


        init()
    }

    private fun init() {
        binding.btLogout.setOnClickListener {
            (activity as HomeActivity).logOut()
        }

        getUserData()
    }

    private fun getUserData() {
        val user = Firebase.auth.currentUser
        user?.let {
            binding.tvProfileName.text = it.displayName
            binding.tvProfileEmail.text = it.email
        }

    }
}