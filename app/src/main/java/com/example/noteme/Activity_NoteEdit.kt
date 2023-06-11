package com.example.noteme

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteme.MainActivity.Companion.flag
import com.example.noteme.databinding.ActivityNoteEditBinding
import com.example.noteme.room.Model
import com.example.noteme.room.Model_obj
import com.example.noteme.viewModel.noteViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.log

class Activity_NoteEdit : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditBinding
    private lateinit var builder : AlertDialog.Builder
    private lateinit var viewModel: noteViewModel
    private var randomColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(noteViewModel::class.java)

        //Seleccionar si editar o crear.
        if(flag){
            GlobalScope.launch {
                setContent()
            }
        }else{
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }

            newColor()
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

            if(binding.editTitle.text.toString().isEmpty()){
                binding.editTitle.error = "Este campo es requerido"
                binding.editTitle.requestFocus()
            }else if(binding.notesEditTextMult.text.toString().isEmpty()) {
                binding.notesEditTextMult.error = "Este campo es requerido"
                binding.notesEditTextMult.requestFocus()
            }else {

                GlobalScope.launch {
                    if (flag) {
                        actualizarNota()
                    } else {
                        guardarNota()
                    }
                }

                finish()

            }
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
    private suspend fun guardarNota(){

        val title = binding.editTitle.text.toString()
        val nota = binding.notesEditTextMult.text.toString()
        val nueva_nota = Model(0,
            title,
            nota,
            Model_obj.date,
            randomColor.toLong(),
            null
        )

        viewModel.guardarNota(nueva_nota)

    }

    //función para actualizar la nota.
    private suspend fun actualizarNota(){
        if(intent.hasExtra("id")){
            val id = intent.getStringExtra("id")?: ""

            val nota =  viewModel.soloUnaNota(id)

            nota?.title = binding.editTitle.text.toString()
            nota?.nota = binding.notesEditTextMult.text.toString()
            nota?.date = Model_obj.date

            viewModel.actualizarNota(nota!!)

        }else{
            Toast.makeText(this, "No se pudo actualizar nota", Toast.LENGTH_LONG).show()
        }
    }

    //Función para eliminar la nota.
    private fun eliminarNota(){
        if(intent.hasExtra("id")){
            builder = AlertDialog.Builder(this)
            val id = intent.getStringExtra("id")?: ""

            builder.setTitle("¿Esta seguro que desea borrar esta nota?")
                .setMessage("Esta accion es irreversible!!!")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    Toast.makeText(this, "Eliminando...", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        val nota = viewModel.soloUnaNota(id)
                        viewModel.eliminarNota(nota!!)
                    }
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
    private suspend fun setContent(){
        if(intent.hasExtra("id")){
            val id = intent.getStringExtra("id")?: ""

            val nota = viewModel.soloUnaNota(id)

            supportActionBar?.apply {
                title = nota?.date
                elevation = 0f
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }

            //Establecer el color al editar Notas.
            val colorInt = nota?.color?.toInt()

            binding.notesEditTextMult.setBackgroundColor(colorInt?: 0)
            binding.editTitle.setBackgroundColor(colorInt?: 0)
            binding.notesCardView.setCardBackgroundColor(colorInt?: 0)
            binding.titleCardView.setCardBackgroundColor(colorInt?: 0)

            binding.editTitle.setText(nota?.title)
            binding.notesEditTextMult.setText(nota?.nota)
        }else{
            Toast.makeText(this, "No se pudieron obtener datos", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    //Función para añadirle color a una nueva carta.
    private fun newColor(){
        val random = Random()

        //generar color aleatorio para la carta (modificado para que genere colores mas claros).
        val alpha = 255
        val red = random.nextInt(100) + 150
        val green = random.nextInt(100) + 150
        val blue = random.nextInt(100) + 150

        randomColor = Color.argb(alpha, red, green, blue)

        binding.notesEditTextMult.setBackgroundColor(randomColor)
        binding.editTitle.setBackgroundColor(randomColor)
        binding.notesCardView.setCardBackgroundColor(randomColor)
        binding.titleCardView.setCardBackgroundColor(randomColor)
    }

}