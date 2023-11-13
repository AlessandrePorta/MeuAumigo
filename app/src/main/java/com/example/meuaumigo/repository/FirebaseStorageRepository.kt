package com.example.meuaumigo.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow

class FirebaseStorageRepository() {

    var firestoreDB = FirebaseFirestore.getInstance()

    fun getPets() = flow { emit(firestoreDB.collection("pets")) }
}