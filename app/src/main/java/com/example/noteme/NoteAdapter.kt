package com.example.noteme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteme.databinding.CardsBinding

class NoteAdapter(var dataList: List<Model>) : RecyclerView.Adapter<NoteAdapter.UserHolder>() {
    inner class UserHolder(val binding: CardsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = CardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        var note= dataList.get(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}