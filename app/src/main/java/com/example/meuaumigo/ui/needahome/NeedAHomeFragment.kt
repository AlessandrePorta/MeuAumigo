package com.example.meuaumigo.ui.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.example.meuaumigo.viewmodel.FirebaseStorageViewModel
import com.google.firebase.firestore.toObjects

class NeedAHomeFragment : Fragment() {

    private lateinit var binding: FragmentNeedAHomeBinding

    private lateinit var petAdapter: NeedAHomeAdapter

    private lateinit var firebaseViewModel: FirebaseStorageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNeedAHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        firebaseViewModel = ViewModelProviders.of(this)[FirebaseStorageViewModel::class.java]

        binding.ibClose.setOnClickListener {
            findNavController().popBackStack()
            (activity as HomeActivity).setNavigateSelectorVisible()
        }

        firebaseViewModel.getPets.observe(requireActivity()) { it.addOnCompleteListener {
            if(it.isSuccessful){
                setupPetList(it.result.toObjects<HomePetVO>())
            }
        }}
        firebaseViewModel.fetchPets()
    }

    private fun setupPetList(petResponse: List<HomePetVO>) {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NeedAHomeAdapter(petResponse, requireContext())

        initAdapter(petResponse)
    }

    private fun initAdapter(response: List<HomePetVO>) {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)

        petAdapter = NeedAHomeAdapter(
            response,
            requireContext()
        )


        rvList.adapter = petAdapter
    }
}