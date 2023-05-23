package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.SeleccionpilotoLayoutBinding
import com.example.proyectostarwiki.models.PilotosData

class SeleccionPilotosViewHoler(v: View): RecyclerView.ViewHolder(v) {
    val binding=SeleccionpilotoLayoutBinding.bind(v)

    fun render(piloto: PilotosData, addItem: (Int) -> Unit){
        binding.tvNombreSeleccionPiloto.text=piloto.nombre
    }
}
