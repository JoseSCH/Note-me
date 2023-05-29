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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setStatusBarStyle()
        setButtonAdd()
        setContentView(binding.root)
        binding.rvRecycler.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(NoteList)
        binding.rvRecycler.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_add){
            val addNota = Intent(this, Activity_NoteEdit::class.java)
            startActivity(addNota)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setStatusBarStyle(){
        supportActionBar?.apply {
            title = "Note Me"
            elevation = 0f
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setButtonAdd(){
        binding.addActionButton.setOnClickListener{
            val agregarNota = Intent(this, Activity_NoteEdit::class.java)
            startActivity(agregarNota)
        }
    }

    companion object{
        var NoteList: ArrayList<Model> = arrayListOf(
            Model(
                "Erase una vez",
                "Hubo una vez que todo en este mundo se perdio de la nada",
                "12/05/2029"
            )
        )
    }

    override fun onRestart() {

        super.onRestart()
    }
}