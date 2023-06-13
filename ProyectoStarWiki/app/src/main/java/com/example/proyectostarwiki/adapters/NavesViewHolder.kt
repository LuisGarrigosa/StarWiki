/**
 * NavesViewHolder.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.NavesLayoutBinding
import com.example.proyectostarwiki.models.NavesData

class NavesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = NavesLayoutBinding.bind(v)

    fun render(nave: NavesData, onItemClick: (NavesData) -> Unit){
        binding.tvNombre.text=nave.nombre
        binding.tvClase.text=nave.clase
        binding.tvCapacidad.text=nave.capacidad
        binding.tvLongitud.text=nave.longitud
        binding.tvPasajeros.text=nave.pasajeros
        binding.tvCreditos.text=nave.creditos
        itemView.setOnClickListener{
            onItemClick(nave)
        }
    }
}
