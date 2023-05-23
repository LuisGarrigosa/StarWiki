package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.PilotosLayoutBinding
import com.example.proyectostarwiki.models.PilotosData

class PilotosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = PilotosLayoutBinding.bind(v)

    fun render(piloto: PilotosData){
        binding.tvNombrePiloto.text=piloto.nombre
        binding.tvGeneroPiloto.text=piloto.genero
        binding.tvAlturaPiloto.text=piloto.altura
        binding.tvPesoPiloto.text=piloto.peso
        binding.tvNombreNave.text=piloto.nombreNave
    }
}
