package com.example.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.noteme.MainActivity.Companion.NoteList
import com.example.noteme.databinding.ActivityNoteEditBinding

class Activity_NoteEdit : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_save){
            guardarNota()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun guardarNota(){
        var title = binding.editTitle.text.toString()
        var nota = binding.notesEditTextMult.text.toString()
        var nueva_nota = Model(
            title,
            nota,
            "100301"
        )

        NoteList.add(nueva_nota)
    }
}