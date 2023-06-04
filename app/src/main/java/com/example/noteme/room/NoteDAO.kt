package com.example.noteme.room

import androidx.room.*
import com.example.noteme.Model

@Dao
interface NoteDAO {

    @Query("SELECT * from Notes")
    fun ListarNotas(): MutableList<Model>

    //Obtener una sola nota
    @Query("SELECT * from Notes WHERE id = :idNota")
    fun soloUnaNota(idNota: Int): Model

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun guardarNota(model: Model)

    @Update
    fun actualizarNota(model: Model)

    @Delete
    suspend fun eliminarNota(model: Model)

}