package com.example.meuaumigo.ui.needahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.example.meuaumigo.viewmodel.FirebaseStorageViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage

class NeedAHomeFragment : Fragment() {

    private lateinit var binding: FragmentNeedAHomeBinding

    private lateinit var petAdapter: NeedAHomeAdapter

    private lateinit var firebaseViewModel: FirebaseStorageViewModel

    private lateinit var firebaseRf : DatabaseReference

    private lateinit var petResponse : ArrayList<HomePetVO>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNeedAHomeBinding.inflate(inflater, container, false)
        firebaseRf = FirebaseDatabase.getInstance().getReference("petData")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petResponse = arrayListOf<HomePetVO>()
        init()
    }

    private fun init() {
//        firebaseViewModel = ViewModelProviders.of(this)[FirebaseStorageViewModel::class.java]
//
//        firebaseViewModel.getPets.observe(requireActivity()) {
//            it.addOnCompleteListener {
//                if (it.isSuccessful) {
//                    petResponse = it.result.toObjects<HomePetVO>()
//                    setupPetList(it.result.toObjects<HomePetVO>())
//                }
//            }
//        }
//        firebaseViewModel.fetchPets()
        initAdapter()
    }

//    private fun setupPetList(petResponse: List<HomePetVO>) {
//        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = NeedAHomeAdapter(petResponse)
//
//        initAdapter(petResponse)
//    }

    private fun initAdapter() {
        val rvList = requireActivity().findViewById<RecyclerView>(R.id.rvNeedAHome)
        rvList.layoutManager = LinearLayoutManager(requireContext())

        firebaseRf.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot : DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnap in snapshot.children){
                        val userData = userSnap.getValue(HomePetVO::class.java)
                        petResponse.add(userData!!)
                    }
                    val mAdapter = NeedAHomeAdapter(petResponse)
                    rvList.adapter = mAdapter
                }
            }

            override fun onCancelled(error : DatabaseError){

            }
        })


    }

}