package com.example.meuaumigo.home.needahome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meuaumigo.home.needahome.model.NeedAHomePetVO

class NeedAHomeViewModel : ViewModel() {

    private val getPets = MutableLiveData<MutableList<NeedAHomePetVO?>>()

    

}