package com.example.meuaumigo.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meuaumigo.home.needahome.model.Pets
import com.example.meuaumigo.repository.FirebaseStorageRepository
import com.google.firebase.firestore.EventListener

class FirebaseStorageViewModel : ViewModel() {

    var getPets : MutableLiveData<HashMap<Pets, Unit>?> = MutableLiveData()
    var firebaseStorageRepository = FirebaseStorageRepository()

    fun getPets(): HashMap<Pets, Unit>? {
        firebaseStorageRepository.getPets().addSnapshotListener(EventListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                getPets.value = null
                return@EventListener
            }

            val savedAddressList : HashMap<Pets, Unit>? = hashMapOf()
            for (doc in value!!) {
                val pets = doc.toObject(Pets::class.java)
                savedAddressList?.get(pets)
            }
            getPets.value = savedAddressList
        })

        return getPets.value
    }

}