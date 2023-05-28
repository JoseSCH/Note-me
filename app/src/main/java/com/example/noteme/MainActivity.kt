package com.example.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import com.example.noteme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setStatusBarStyle()
        setButtonAdd()
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_add){
            val agregarNota = Intent(this, Activity_NoteEdit::class.java)
            startActivity(agregarNota)
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
}