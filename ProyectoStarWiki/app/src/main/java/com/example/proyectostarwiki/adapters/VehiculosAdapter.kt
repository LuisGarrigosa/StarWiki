/**
 * VehiculosAdapter.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.VehiculosData

class VehiculosAdapter(var lista: MutableList<VehiculosData>,
        var onItemClick: (VehiculosData)->Unit): RecyclerView.Adapter<VehiculosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculosViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vehiculos_layout, parent, false)
        return VehiculosViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: VehiculosViewHolder, position: Int) {
       holder.render(lista[position], onItemClick)
    }
}