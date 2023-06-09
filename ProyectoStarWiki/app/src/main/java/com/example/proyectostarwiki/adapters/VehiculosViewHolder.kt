package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.VehiculosLayoutBinding
import com.example.proyectostarwiki.models.VehiculosData

class VehiculosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = VehiculosLayoutBinding.bind(v)

    fun render(vehiculo: VehiculosData, onItemClick: (VehiculosData) -> Unit){
        binding.tvNombreVeh.text=vehiculo.nombre
        binding.tvModeloVeh.text=vehiculo.modelo
        binding.tvAlturaVeh.text=vehiculo.altura
        binding.tvFabriVeh.text=vehiculo.fabricante
        binding.tvVelVeh.text=vehiculo.velocidad
        itemView.setOnClickListener{
            onItemClick(vehiculo)
        }
    }
}
