package com.example.meuaumigo.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseStorageRepository() {

    var firestoreDB = FirebaseFirestore.getInstance()

    fun getPets() : CollectionReference {
        val getPets = firestoreDB.collection("pets")
        return getPets
    }
}