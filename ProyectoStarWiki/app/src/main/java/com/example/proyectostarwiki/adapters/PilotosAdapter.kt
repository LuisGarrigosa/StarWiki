/**
 * PilotosAdapterAdapter.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.PilotosData

class PilotosAdapter(var lista: MutableList<PilotosData>): RecyclerView.Adapter<PilotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilotosViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pilotos_layout, parent, false)
        return PilotosViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: PilotosViewHolder, position: Int) {
        holder.render(lista[position])
    }

}