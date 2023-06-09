package com.example.noteme

import android.content.Intent
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.noteme.MainActivity.Companion.flag
import com.example.noteme.databinding.CardsBinding
import com.example.noteme.room.Model

class NoteAdapter(var dataList: List<Model>) : RecyclerView.Adapter<NoteAdapter.UserHolder>() {
    inner class UserHolder(val binding: CardsBinding) : RecyclerView.ViewHolder(binding.root)
    private var actionMode: ActionMode? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = CardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards, parent, false)

        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        var note = dataList.get(position)
        holder.binding.RVTitulo.text = note.title
        holder.binding.RVNota.text = note.nota
        holder.binding.RVFecha.text = note.date

        //agregar color a la carta según la base de datos.
        holder.binding.LinearCard.setBackgroundColor(note.color)
        holder.binding.RVNota.setBackgroundColor(note.color)
        holder.binding.RVFecha.setBackgroundColor(note.color)
        holder.binding.RVTitulo.setBackgroundColor(note.color)

        //listener en caso de presionar una nota para editar.
        holder.binding.editCardView.setOnClickListener{
            val editar = Intent(holder.itemView.context, Activity_NoteEdit::class.java)
            editar.putExtra("id", note.id)
            flag = true
            holder.itemView.context.startActivity(editar)
        }

        //Implementacion de obciones a la hora de presionar.
        holder.binding.editCardView.setOnLongClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_delete -> {
                        // Acción a realizar al seleccionar "Eliminar"
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
            true
        }

    }
    //Obtener la cantidad de items.
    override fun getItemCount(): Int {
        return dataList.size
    }

}