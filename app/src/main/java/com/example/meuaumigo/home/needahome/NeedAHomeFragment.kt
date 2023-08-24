package com.example.meuaumigo.home.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.databinding.ItemPetNeedAHomeBinding
import com.example.meuaumigo.home.needahome.model.PetVO

class NeedAHomeFragment : Fragment() {

    private lateinit var binding : FragmentNeedAHomeBinding

    private lateinit var petAdapter : NeedAHomeAdapter

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

    private fun init(){
        binding.ibClose.setOnClickListener {
            findNavController().popBackStack()
        }

        setupPetList(pets())
    }

    private fun pets() : MutableList<PetVO>{
        val pets = ArrayList<PetVO>()
        pets.add(PetVO("Jorginho", R.drawable.ic_main_pet))
        pets.add(PetVO("Cleiton", R.drawable.ic_main_pet))
        pets.add(PetVO("Fofo", R.drawable.ic_main_pet))
        return pets
    }

    private fun setupPetList(petResponse: MutableList<PetVO>) {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NeedAHomeAdapter(petResponse, requireContext())

        initAdapter(petResponse)
    }

    private fun initAdapter(response: MutableList<PetVO>) {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)

        petAdapter = NeedAHomeAdapter(
            response,
            requireContext()
        )

        rvList.adapter = petAdapter
    }
}