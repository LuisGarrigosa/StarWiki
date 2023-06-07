package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.PeliculasData

class PeliculasAdapter(var lista: MutableList<PeliculasData>): RecyclerView.Adapter<PeliculasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.peliculas_layout, parent, false)
        return PeliculasViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: PeliculasViewHolder, position: Int) {
        holder.render(lista[position])
    }
}