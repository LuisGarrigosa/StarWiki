package com.example.proyectostarwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.models.PilotosData

class SeleccionPilotosAdapter(
    var lista: MutableList<PilotosData>,
    var addItem: (Int)->Unit,): RecyclerView.Adapter<SeleccionPilotosViewHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeleccionPilotosViewHoler {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.seleccionpiloto_layout, parent, false)
        return SeleccionPilotosViewHoler(v)
    }

    override fun onBindViewHolder(holder: SeleccionPilotosViewHoler, position: Int) {
        val piloto = lista[position]
        holder.render(piloto, addItem)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}