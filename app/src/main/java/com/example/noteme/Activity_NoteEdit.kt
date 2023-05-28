package com.example.noteme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteme.databinding.ActivityNoteEditBinding

class Activity_NoteEdit : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}