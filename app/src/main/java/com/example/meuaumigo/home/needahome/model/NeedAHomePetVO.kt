package com.example.meuaumigo.home.needahome.model
data class NeedAHomePetVO (
    var petName : String = "",
    var petImg : Int = 0
    )

data class Pets (
    var pets : HashMap<NeedAHomePetVO, String> = hashMapOf()
        )
