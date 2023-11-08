package com.example.meuaumigo.home.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.home.homemain.HomeActivity
import com.example.meuaumigo.home.needahome.model.NeedAHomePetVO
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlin.concurrent.timerTask

class NeedAHomeFragment : Fragment() {

    private lateinit var binding : FragmentNeedAHomeBinding

    private lateinit var petAdapter : NeedAHomeAdapter

    val pets = mutableListOf<NeedAHomePetVO>()

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
            (activity as HomeActivity).setNavigateSelectorVisible()
        }

        pets()
        setupPetList(pets)
    }

    private fun pets(){
        val db = Firebase.firestore
        db.collection("pets")
            .get()
                .addOnSuccessListener {
                for (document in it) {
                    pets.add(NeedAHomePetVO(document.id, R.drawable.ic_main_pet))
                }
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Erro ao carregar", Toast.LENGTH_LONG).show()
                }

    }

    private fun setupPetList(petResponse: MutableList<NeedAHomePetVO>) {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NeedAHomeAdapter(petResponse, requireContext())

        initAdapter(petResponse)
    }

    private fun initAdapter(response: MutableList<NeedAHomePetVO>) {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)

        petAdapter = NeedAHomeAdapter(
            response,
            requireContext()
        )

        rvList.adapter = petAdapter
    }
}