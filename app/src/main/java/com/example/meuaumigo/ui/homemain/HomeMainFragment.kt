package com.example.meuaumigo.ui.homemain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentHomeBinding

class HomeMainFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.ivNeedAHomeMe.setOnClickListener {
            findNavController().navigate(R.id.action_homeMainFragment_to_needAHomeFragment)
            (activity as HomeActivity).setNavigateSelectorInvisible()
        }
        binding.ivLookingForHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeMainFragment_to_lookingForHomeFragment)
            (activity as HomeActivity).setNavigateSelectorInvisible()
        }
    }

}