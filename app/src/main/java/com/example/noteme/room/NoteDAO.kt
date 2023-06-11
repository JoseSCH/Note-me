package com.example.noteme.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    //Listar todas las notas.
    @Query("SELECT * from Notes")
    fun ListarNotas(): Flow<List<Model>>

    //Guardar las notas.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun guardarNota(model: Model)

    //Actualizar las notas.
    @Update
    fun actualizarNota(model: Model)

    @Query("DELETE FROM Notes")
    suspend fun eliminarTodasLasNotas()

    //Eliminar una nota.
    @Delete
    suspend fun eliminarNota(model: Model)

}