/**
 * MensajesAdapter.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.Mensajes

class MensajesAdapter(var lista:ArrayList<Mensajes>, private val email: String): RecyclerView.Adapter<MensajesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajesViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.mensaje_layout, parent, false)
        return MensajesViewHolder(v)
    }

    override fun onBindViewHolder(holder: MensajesViewHolder, position: Int) {
        holder.render(lista[position], email)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}