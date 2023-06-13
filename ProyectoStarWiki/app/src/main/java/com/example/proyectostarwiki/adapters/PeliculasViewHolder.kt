/**
 * PeliculasViewHolder.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.PeliculasLayoutBinding
import com.example.proyectostarwiki.models.PeliculasData

class PeliculasViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = PeliculasLayoutBinding.bind(v)

    fun render(pelicula: PeliculasData){
        binding.tvNombrePeli.text=pelicula.nombre
        binding.tvDirectorPeli.text=pelicula.director
        binding.tvProductorPeli.text=pelicula.productor
        binding.tvEstrenoPeli.text=pelicula.estreno
    }

}
