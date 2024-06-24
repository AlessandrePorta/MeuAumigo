package com.example.meuaumigo.ui.register

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentRegisterBinding
import com.example.meuaumigo.model.UserVO
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Delay

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var firebaseRef: DatabaseReference

    private lateinit var fireStorage: StorageReference

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("UserData")
        fireStorage = FirebaseStorage.getInstance().getReference("UserImage")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val pickImg = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivLogin.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }
        binding.ivLogin.setOnClickListener {
            pickImg.launch("image/*")
        }
        binding.btnRegister.setOnClickListener {
            if (binding.etName.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite um nome", Toast.LENGTH_LONG).show()
            } else if (binding.etLogin.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite um email válido", Toast.LENGTH_LONG).show()
            } else if (binding.etPassword.text.toString()
                    .isNullOrEmpty() && binding.etConfirmPassword.text.toString().isNullOrEmpty()
            ) {
                Toast.makeText(requireContext(), "Digite uma senha válida", Toast.LENGTH_LONG)
                    .show()
            } else if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
                Toast.makeText(
                    requireContext(),
                    "Senha e confirmação de senha não conferem.",
                    Toast.LENGTH_LONG
                ).show()
            } else if(binding.etPhone.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Digite um telefone válido", Toast.LENGTH_LONG)
                    .show()
            } else {
                (activity as HomeActivity).createAccount(
                    binding.etLogin.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etName.text.toString()
                )
                Handler().postDelayed({
                    saveData()
                }, 5000)

            }
        }
        binding.btnAlreadyHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun saveData() {
        val uidAuth = FirebaseAuth.getInstance().uid!!
        val userId = firebaseRef.push().key!!
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        var userVO : UserVO

        if(uri != null) {
            uri?.let {
                fireStorage.child(uidAuth).putFile(it)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                val imgUrl = url.toString()

                                userVO = UserVO(userId, name, phone, imgUrl)
                                firebaseRef.child(uidAuth).setValue(userVO)
                                    .addOnCompleteListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Conta criada com sucesso!",
                                            Toast.LENGTH_LONG
                                        ).show()
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
        } else {
            userVO = UserVO(userId, phone, null)
            firebaseRef.child(userId).setValue(userVO)
                .addOnCompleteListener {
                    Toast.makeText(
                        requireContext(),
                        "FUNCIONOU",
                        Toast.LENGTH_LONG
                    ).show()
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