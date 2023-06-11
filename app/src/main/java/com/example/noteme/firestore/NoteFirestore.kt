package com.example.noteme.firestore

import android.util.Log
import com.example.noteme.room.Model
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

object NoteFirestore {
    suspend fun getNotes(): List<Model> = coroutineScope {
        val db = Firebase.firestore
        val listaNotas = mutableListOf<Model>()

        try{
            val querySnapshot = db.collection("NoteMe").get().await()
            listaNotas.addAll(getListNotes(querySnapshot))
        }catch (e: Exception){
            Log.e("Error", "Error fetching documents", e)
        }
        listaNotas
    }

    suspend fun createNote(model: Model) = coroutineScope {
        val db = Firebase.firestore
        db.collection("NoteMe").add(model).await()
    }

    suspend fun updateNote(model: Model) = coroutineScope {
        val db = Firebase.firestore
        val newNoteData = hashMapOf<String, Any>(
            "title" to model.title,
            "nota" to model.nota,
            "date" to model.date,
            "color" to model.color
        )
        db.collection("NoteMe").document(model.idFirestore!!).update(newNoteData).await()
    }

    suspend fun deleteNote(model: Model) = coroutineScope {
        val db = Firebase.firestore
        db.collection("NoteMe").document(model.idFirestore!!).delete()
    }

    fun getListNotes(querySnapshot: QuerySnapshot):List<Model> {
        val listNotes = mutableListOf<Model>()

        for (document in querySnapshot.documents){
            val noteData = document.data
            val noteModel = noteData?.let { convertToNoteModel(it, document.id) }

            listNotes.add(noteModel!!)
        }

        return listNotes
    }

    fun convertToNoteModel(noteData: Map<String, Any>, id: String): Model {
        val title = noteData["title"] as String
        val note = noteData["nota"] as String
        val date = noteData["date"] as String
        val color = noteData["color"] as Long

        return Model(0, title, note, date, color, id)
    }
}