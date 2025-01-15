package com.example.meuaumigo.ui.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.example.meuaumigo.model.UserVO
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NeedAHomeFragment : Fragment() {

    private lateinit var binding: FragmentNeedAHomeBinding

    private lateinit var firebaseRfPet: DatabaseReference

    private lateinit var firebaseRfUser: DatabaseReference

    private lateinit var petResponse: ArrayList<HomePetVO>

    private lateinit var userResponse: UserVO

    private var color = 1

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

        userResponse = UserVO("", "", "", "")
        petResponse = arrayListOf()
        init()
    }

    private fun init() {
        (activity as HomeActivity).showLoading(true)
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
            if (it.exists()) {
                userResponse = UserVO(
                    it?.child("id")?.value.toString(),
                    it?.child("name")?.value.toString(),
                    it?.child("phoneNumber")?.value.toString(),
                    it?.child("imgUri")?.value.toString()
                )
                binding.tvHomeName.text = it?.child("name")?.value.toString()
                Glide.with(requireContext()).load(it?.child("imgUri")?.value.toString())
                    .into(binding.ivProfileImg).request?.isComplete.apply {
                        (activity as HomeActivity).showLoading(false)
                    }
            }
        }
    }

    private fun initAdapter() {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        rvList.layoutManager = LinearLayoutManager(requireContext())

        firebaseRfPet.get().addOnSuccessListener {
            if (it.exists()) {
                for (userSnap in it.children) {
                    val petData = userSnap.getValue(HomePetVO::class.java)
                    petResponse.add(petData!!)
                }
                val mAdapter =
                    NeedAHomeAdapter(petResponse, onPetClicked = ::navigateToPetDetails, onLikeClicked = ::onLikeClicked)
                rvList.adapter = mAdapter
            }
        }
    }

    private fun navigateToPetDetails(homeVO: HomePetVO) {
        (activity as HomeActivity).showLoading(true)
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

    private fun navigateToProfile() {
        findNavController().navigate(
            NeedAHomeFragmentDirections.actionNeedAHomeFragmentToHomeProfileFragment(
                userResponse.name.toString(),
                img = userResponse.imgUri.toString(),
                localization = ""
            )
        )
    }

    private fun onLikeClicked(btn : ImageButton){
        if(color == 0) {
            btn.setBackgroundResource(R.drawable.ic_like)
            Toast.makeText(context, "Removido dos favoritos!", Toast.LENGTH_SHORT).show()
            color = 1
        } else {
            btn.setBackgroundResource(R.drawable.ic_like_clicked)
            Toast.makeText(context, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
            color = 0
        }
    }
}