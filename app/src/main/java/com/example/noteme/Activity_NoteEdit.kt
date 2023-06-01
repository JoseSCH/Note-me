package com.example.noteme

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.example.noteme.MainActivity.Companion.NoteList
import com.example.noteme.MainActivity.Companion.flag
import com.example.noteme.databinding.ActivityNoteEditBinding
import java.util.Calendar

class Activity_NoteEdit : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Seleccionar si editar o crear.
        if(flag){
            setContent()
        }else{
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }


    //inflar menu.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)

        //Remover el botón eliminar, solo se muestra al editar nota.
        if(!flag){
            menu?.removeItem(R.id.menu_delete)
        }

        return super.onCreateOptionsMenu(menu)
    }

    //Listeners para cada opcion del menu.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            if(flag){
                actualizarNota()
            }else{
                guardarNota()
            }
            finish()
        }

        //Para que al presionar la flecha de retroceso regrese a la vista anterior.
        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }

        if(item.itemId == R.id.menu_delete){
            eliminarNota()
        }

        return super.onOptionsItemSelected(item)
    }

    //función para guardar la nota.
    private fun guardarNota(){
        var title = binding.editTitle.text.toString()
        var nota = binding.notesEditTextMult.text.toString()
        var nueva_nota = Model(
            title,
            nota,
            Model_obj.date
        )

        NoteList.add(nueva_nota)
    }

    //función para actualizar la nota.
    private fun actualizarNota(){
        if(intent.hasExtra("position")){
            var pocision = intent.getIntExtra("position", 0)

            NoteList[pocision].title = binding.editTitle.text.toString()
            NoteList[pocision].nota = binding.notesEditTextMult.text.toString()
            NoteList[pocision].date = Model_obj.date
            finish()
        }else{
            Toast.makeText(this, "No se pudo actualizar nota", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    //Función para eliminar la nota.
    private fun eliminarNota(){
        if(intent.hasExtra("position")){
            builder = AlertDialog.Builder(this)
            var posicion = intent.getIntExtra("position", 0)

            builder.setTitle("¿Esta seguro que desea borrar esta nota?")
                .setMessage("Esta accion es irreversible!!!")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    Toast.makeText(this, "Eliminando...", Toast.LENGTH_SHORT).show()
                    NoteList.removeAt(posicion)
                    finish()
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    Toast.makeText(this, "Accion Cancelada", Toast.LENGTH_SHORT).show()
                    dialogInterface.cancel()
                }
                .show()
        }else{
            Toast.makeText(this, "No se pudo eliminar nota", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    //función para agregar contenido en caso de edición de nota.
    private fun setContent(){
        if(intent.hasExtra("position")){
            var pocision = intent.getIntExtra("position", 0)

            supportActionBar?.apply {
                title = NoteList[pocision].date
                elevation = 0f
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }

            binding.editTitle.setText(NoteList[pocision].title)
            binding.notesEditTextMult.setText(NoteList[pocision].nota)
        }else{
            Toast.makeText(this, "No se pudieron obtener datos", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    //Guardar nota en como respaldo en caso de que se cierre o en caso de olvidar guardar.
    override fun finish() {
        if(!flag){
            guardarNota()
        }
        super.finish()
    }
}