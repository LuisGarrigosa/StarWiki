package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.NavesData

class NavesAdapter(var lista: MutableList<NavesData>): RecyclerView.Adapter<NavesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.naves_layout, parent, false)
        return NavesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: NavesViewHolder, position: Int) {
        holder.render(lista[position])
    }
}