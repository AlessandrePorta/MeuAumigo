package com.example.meuaumigo.model

import android.net.Uri

data class HomePetVO(
    var petName: String? = null,
    var petSex : String? = null,
    var petBreed : String? = null,
    var petSize : String? = null,
    var petAge : String? = null,
    var petLocalization : String? = null,
    var petDescription: String? = "",
    val petImg : String? = null
)