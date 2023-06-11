package com.example.noteme.viewModel

import android.app.Application
import com.example.noteme.room.Model
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteme.repository.NoteRepository
import com.example.noteme.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class noteViewModel(application: Application) : AndroidViewModel(application) {
    val ListaNotas : MutableLiveData<List<Model>> = MutableLiveData()
    val repository : NoteRepository

    init{
        val notesDao = NotesDatabase.getDatabase(application).NoteDAO()
        repository = NoteRepository(notesDao)
        //ListaNotas = repository.ListarNotas()
        getNotesFirestore()
    }

    //Guardar una nota.
    fun guardarNota(nota: Model) =
        viewModelScope.launch(Dispatchers.IO) { repository.guardarNota(nota) }

    //Obtener una nota.
    suspend fun soloUnaNota(idNota: String) : Model? =
        withContext(Dispatchers.IO){
            repository.soloUnaNota(idNota)
        }

    //Actualizat una nota.
    suspend fun actualizarNota(nota: Model) =
        viewModelScope.launch(Dispatchers.IO) { repository.actualizarNota(nota) }

    //Eliminar una nota.
    suspend fun eliminarNota(nota: Model) =
        viewModelScope.launch(Dispatchers.IO) { repository.eliminarNota(nota) }

    fun getNotesFirestore(){
        viewModelScope.launch(Dispatchers.IO) {
            var list = repository.listarNotasFirestore()
            ListaNotas.postValue(list)
        }
    }
}