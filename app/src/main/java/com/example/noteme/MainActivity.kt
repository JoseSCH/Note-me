package com.example.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteme.databinding.ActivityMainBinding
import com.example.noteme.room.Model
import com.example.noteme.room.Model_obj
import com.example.noteme.room.NotesDatabase
import com.example.noteme.viewModel.noteViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var viewModel: noteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setActionBarStyle()
        setButtonAdd()

        GlobalScope.launch {
            getActualDate()
        }

        setContentView(binding.root)
        binding.rvRecycler.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(noteViewModel::class.java)

        cargarNotas()
    }

    //inflar menu.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Listener para cada item del menu.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_search){

        }

        return super.onOptionsItemSelected(item)
    }

    //Aplicar estilo al action bar.
    private fun setActionBarStyle(){
        supportActionBar?.apply {
            title = "Note Me"
            elevation = 0f
        }
    }

    //Listener para el botón agregar.
    private fun setButtonAdd(){
        binding.addActionButton.setOnClickListener{
            flag = false
            val agregarNota = Intent(this, Activity_NoteEdit::class.java)
            startActivity(agregarNota)
        }
    }


    //Cargar los datos desde nuestra base de datos.
    private fun cargarNotas(){
        viewModel.ListaNotas.observe(this) { lista ->
            lista?.let {
                adapter = NoteAdapter(it)
                binding.rvRecycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    //Objetos compartidos.
    companion object{
        //flags para vistas
        var flag = false

        lateinit var oneNote: Model

        //var listNotes: MutableList<Model> = mutableListOf()
        //var dataBaseInstance: NotesDatabase? = null
    }

    //funcion para obtener la fecha actual.
    private fun getActualDate(){
        val calendario = Calendar.getInstance()
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH) + 1
        val anio = calendario.get(Calendar.YEAR)
        Model_obj.date = String.format("%02d/%02d/%04d", dia, mes, anio)
    }

    //Funcion para actualizar el adaptador
    override fun onRestart() {
        viewModel.getNotesFirestore()
        super.onRestart()
    }

}