/**
 * PlanetasAdapter.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.PlanetasData

class PlanetasAdapter(var lista: MutableList<PlanetasData>): RecyclerView.Adapter<PlanetasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetasViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.planetas_layout, parent, false)
        return PlanetasViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlanetasViewHolder, position: Int) {
        holder.render(lista[position])
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}