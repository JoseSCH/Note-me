package com.example.noteme.repository

import com.example.noteme.room.Model
import androidx.lifecycle.asLiveData
import androidx.lifecycle.LiveData
import com.example.noteme.room.NoteDAO

class NoteRepository constructor(
    private val noteDao: NoteDAO
) {

    //para listar todas las notas.
    fun ListarNotas(): LiveData<List<Model>> = noteDao.ListarNotas().asLiveData()

    //Guardar una nota.
    suspend fun guardarNota(nota: Model){
        noteDao.guardarNota(nota)
    }

    //Obtener un Nota.
    fun soloUnaNota(idNota: Int): Model = noteDao.soloUnaNota(idNota)

    //Actualizat una nota.
    suspend fun actualizarNota(model: Model){
        noteDao.actualizarNota(model)
    }

    suspend fun EliminarTodasLasNotas(){
        noteDao.eliminarTodasLasNotas()
    }

    //Eliminar una nota.
    suspend fun eliminarNota(nota: Model){
        noteDao.eliminarNota(nota)
    }

}