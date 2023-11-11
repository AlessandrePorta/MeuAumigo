package com.example.meuaumigo.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meuaumigo.home.needahome.model.NeedAHomePetVO
import com.example.meuaumigo.repository.FirebaseStorageRepository

class FirebaseStorageViewModel : ViewModel() {

    var getPets : MutableLiveData<MutableList<NeedAHomePetVO>?> = MutableLiveData()
    var firebaseStorageRepository = FirebaseStorageRepository()

    fun getPets(){
        firebaseStorageRepository.getPets()
            .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val pets = MutableLiveData<MutableList<String>?>()
            for (doc in value!!) {
                doc.getString("name")?.let {
                    pets.value?.add(it)
                    getPets.value?.first()?.petName = pets.value?.first().toString()
                }
            }
            Log.d(TAG, "Current cites in CA: $pets")
        }

    }

}