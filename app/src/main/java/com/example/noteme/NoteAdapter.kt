package com.example.noteme

import android.animation.ArgbEvaluator
import android.content.Intent
import android.graphics.Color
import android.icu.text.MessagePattern.ArgType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.noteme.MainActivity.Companion.flag
import com.example.noteme.databinding.CardsBinding
import java.util.Random

class NoteAdapter(var dataList: List<Model>) : RecyclerView.Adapter<NoteAdapter.UserHolder>() {
    inner class UserHolder(val binding: CardsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = CardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val random = Random()
        var note = dataList.get(position)
        holder.binding.RVTitulo.text = note.title
        holder.binding.RVNota.text = note.nota
        holder.binding.RVFecha.text = note.date

        //generar color aleatorio para la carta.
        val alpha = 255
        val red = random.nextInt(156) + 100
        val green = random.nextInt(156)
        val blue = random.nextInt(156) + 100

        val randomColor = Color.argb(alpha, red, green, blue)

        //agregar color aleatorio a la carta.
        holder.binding.LinearCard.setBackgroundColor(randomColor)
        holder.binding.RVNota.setBackgroundColor(randomColor)
        holder.binding.RVFecha.setBackgroundColor(randomColor)
        holder.binding.RVTitulo.setBackgroundColor(randomColor)

        //listener en caso de presionar una nota para editar.
        holder.binding.editCardView.setOnClickListener{
            val editar = Intent(holder.itemView.context, Activity_NoteEdit::class.java)
            editar.putExtra("position", position)
            flag = true
            holder.itemView.context.startActivity(editar)
        }
    }

    //Obtener la cantidad de items.
    override fun getItemCount(): Int {
        return dataList.size
    }
}