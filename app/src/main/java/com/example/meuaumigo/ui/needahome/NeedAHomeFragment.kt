package com.example.meuaumigo.ui.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.core.snap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.example.meuaumigo.model.UserVO
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class NeedAHomeFragment : Fragment() {

    private lateinit var binding: FragmentNeedAHomeBinding

    private lateinit var firebaseRfPet: DatabaseReference

    private lateinit var firebaseRfUser: DatabaseReference

    private lateinit var petResponse: ArrayList<HomePetVO>

    private lateinit var userResponse : UserVO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNeedAHomeBinding.inflate(inflater, container, false)
        firebaseRfPet = FirebaseDatabase.getInstance().getReference("petData")
        firebaseRfUser = FirebaseDatabase.getInstance().getReference("UserData")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petResponse = arrayListOf()
        init()
    }

    private fun init() {
        initAdapter()
        getUserData()
        binding.ivProfileImg.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun getUserData() {
        var uid = FirebaseAuth.getInstance().uid!!
        val uidRef = firebaseRfUser.child(uid)
        uidRef.get().addOnSuccessListener {
            if(it.exists()){
                userResponse = UserVO(it?.child("id")?.value.toString(), it?.child("name")?.value.toString(), it?.child("phoneNumber")?.value.toString(), it?.child("imgUri")?.value.toString())
                binding.tvHomeName.text = it?.child("name")?.value.toString()
                Glide.with(requireContext()).load(it?.child("imgUri")?.value.toString()).into(binding.ivProfileImg)
            }
        }
    }

    private fun initAdapter() {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        rvList.layoutManager = LinearLayoutManager(requireContext())

        firebaseRfPet.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val petData = userSnap.getValue(HomePetVO::class.java)
                        petResponse.add(petData!!)
                    }
                    val mAdapter =
                        NeedAHomeAdapter(petResponse, onPetClicked = ::navigateToPetDetails)
                    rvList.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateToPetDetails(homeVO: HomePetVO) {
        findNavController()
            .navigate(
                NeedAHomeFragmentDirections.actionNeedAHomeFragmentToPetDetailsFragment(
                    homeVO.petName.toString(),
                    homeVO.petBreed.toString(),
                    homeVO.petAge.toString(),
                    homeVO.petLocalization.toString(),
                    homeVO.petDescription.toString(),
                    homeVO.petImg.toString()
                )
            )

    }

    private fun navigateToProfile(){
        findNavController().navigate(NeedAHomeFragmentDirections.actionNeedAHomeFragmentToHomeProfileFragment(
            userResponse.name.toString(),
            img = userResponse.imgUri.toString(),
            localization = ""
        ))
    }
}