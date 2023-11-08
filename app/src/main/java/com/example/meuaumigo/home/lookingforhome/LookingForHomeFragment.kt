package com.example.meuaumigo.home.lookingforhome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.databinding.FragmentLookingForHomeBinding
import com.example.meuaumigo.home.homemain.HomeActivity
import com.example.meuaumigo.home.lookingforhome.model.LookingForHomePetVO
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

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
                LookingForHomePetVO(hashMapOf(
                    "name" to binding.etPetName.text.toString(),
                    "phone" to binding.etPetPhone.text.toString(),
                    "description" to binding.etPetDescription.text.toString()
                ))
            )
            (activity as HomeActivity).setNavigateSelectorVisible()
        }

    }

    private fun createPetData(pets: LookingForHomePetVO) {
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