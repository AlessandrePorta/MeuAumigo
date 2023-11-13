package com.example.meuaumigo.ui.lookingforhome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.databinding.FragmentLookingForHomeBinding
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.example.meuaumigo.model.HomePetVO
import com.google.firebase.firestore.FirebaseFirestore

class LookingForHomeFragment : Fragment() {

    private lateinit var binding: FragmentLookingForHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLookingForHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.ibClose.setOnClickListener {
            findNavController().popBackStack()
            (activity as HomeActivity).setNavigateSelectorVisible()
        }
        binding.btnConfirm.setOnClickListener {
            createPetData(
                HomePetVO(
                    binding.etPetName.text.toString(),
                    binding.etPetPhone.text.toString(),
                    binding.etPetDescription.text.toString()
                )
            )
            (activity as HomeActivity).setNavigateSelectorVisible()
        }

    }

    private fun createPetData(pets: HomePetVO) {
        val db = FirebaseFirestore.getInstance()
        db.collection("pets").document(binding.etPetName.text.toString())
            .set(pets)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Sucesso!", Toast.LENGTH_LONG).show()
                Log.d("", "Sucesso!")
                findNavController().popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Erro!", Toast.LENGTH_LONG).show()
                Log.e("", "Erro")
            }
    }

}