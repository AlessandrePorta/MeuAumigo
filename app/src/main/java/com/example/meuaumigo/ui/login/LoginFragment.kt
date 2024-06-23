package com.example.meuaumigo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentLoginBinding
import com.example.meuaumigo.ui.homemain.HomeActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            if (!binding.etLogin.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()) {
                (activity as HomeActivity).signIn(
                    binding.etLogin.text.toString(),
                    binding.etPassword.text.toString()
                )
            } else {
                Toast.makeText(requireContext(), "E-mail ou senha invalidos", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}