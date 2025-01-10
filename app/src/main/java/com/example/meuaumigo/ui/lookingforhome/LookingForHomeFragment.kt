package com.example.meuaumigo.ui.lookingforhome

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.databinding.FragmentLookingForHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class LookingForHomeFragment : Fragment() {

    private lateinit var binding: FragmentLookingForHomeBinding

    private var uri: Uri? = null

    private lateinit var firebaseRef: DatabaseReference

    private lateinit var fireStorage: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLookingForHomeBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("petData")
        fireStorage = FirebaseStorage.getInstance().getReference("petImage")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val pickImg = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivPetImg.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.ivPetImg.setOnClickListener {
            pickImg.launch("image/*")
        }

        binding.btnRegisterPet.setOnClickListener {
            if (binding.etPetName.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite o nome", Toast.LENGTH_LONG).show()
            } else if (binding.etPetSex.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite o sexo do pet", Toast.LENGTH_LONG).show()
            } else if (binding.etPetBreed.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite a raça do pet", Toast.LENGTH_LONG)
                    .show()
            } else if (binding.etPetSize.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Digite o tamanho do pet",
                    Toast.LENGTH_LONG
                ).show()
            } else if (binding.etPetAge.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite a idade do pet", Toast.LENGTH_LONG)
                    .show()
            } else if (binding.etPetLocalization.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Digite onde o pet está localizado",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else if (binding.etPetDescription.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite a descrição do pet", Toast.LENGTH_LONG)
                    .show()
            } else if (uri == null) {
                Toast.makeText(requireContext(), "Selecione uma foto", Toast.LENGTH_LONG).show()
            } else {
                getPetImageData()
            }
        }
    }

    private fun getPetImageData() {
        val userId = firebaseRef.push().key!!
        var petsVO: HomePetVO

        uri?.let {
            fireStorage.child(userId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val imgUrl = uri.toString()

                            petsVO = HomePetVO(
                                binding.etPetName.text.toString(),
                                binding.etPetSex.text.toString(),
                                binding.etPetBreed.text.toString(),
                                binding.etPetSize.text.toString(),
                                binding.etPetAge.text.toString(),
                                binding.etPetLocalization.text.toString(),
                                binding.etPetDescription.text.toString(),
                                imgUrl
                            )

                            firebaseRef.child(userId).setValue(petsVO)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Pet adicionado com sucesso!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    findNavController().popBackStack()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "error ${it.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                }
        }

    }


}