package com.example.meuaumigo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meuaumigo.databinding.FragmentProfileBinding

class HomeProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val args by navArgs<HomeProfileFragmentArgs>()

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

        init()
    }

    private fun init() {
        getUserData()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvRegisterPet.setOnClickListener {
            findNavController().navigate(HomeProfileFragmentDirections.actionHomeProfileFragmentToLookingForHomeFragment())

        }
    }

    private fun getUserData() = with(binding) {
        binding.tvName.text = args.name
        Glide.with(requireContext()).load(args.img).into(ivProfileImg)

    }
}