package com.example.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteme.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setActionBarStyle()
        setButtonAdd()
        getActualDate()
        setContentView(binding.root)
        binding.rvRecycler.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(NoteList)
        binding.rvRecycler.adapter = adapter
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

    //Objetos compartidos.
    companion object{
        //flags para vistas
        var flag = false

        //Lista de notas
        var NoteList: ArrayList<Model> = arrayListOf(
            Model(
                "Erase una vez",
                "Hubo una vez que todo en este mundo se perdio de la nada",
                "12/05/2029"
            )
        )
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
        adapter.notifyDataSetChanged()
        super.onRestart()
    }
}