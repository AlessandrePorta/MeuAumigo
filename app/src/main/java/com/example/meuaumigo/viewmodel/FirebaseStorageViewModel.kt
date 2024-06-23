package com.example.meuaumigo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.meuaumigo.repository.FirebaseStorageRepository
import com.example.meuaumigo.utils.toLiveData

class FirebaseStorageViewModel : ViewModel() {

    var _getPets = MutableLiveData<Unit>()
    var firebaseStorageRepository = FirebaseStorageRepository()

    val getPets = _getPets.switchMap {
        firebaseStorageRepository.getPets().toLiveData()
    }

    fun fetchPets() {
        _getPets.postValue(Unit)
    }

}