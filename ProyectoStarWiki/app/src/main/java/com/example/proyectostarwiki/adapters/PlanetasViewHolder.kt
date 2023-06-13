/**
 * PlanetasViewHolder.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.databinding.PlanetasLayoutBinding
import com.example.proyectostarwiki.models.PlanetasData

class PlanetasViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = PlanetasLayoutBinding.bind(v)

    fun render(planeta: PlanetasData){
        binding.tvNombrePla.text=planeta.nombre
        binding.tvDiametroPla.text=planeta.diametro
        binding.tvGravedadPla.text=planeta.gravedad
        binding.tvPoblacionPla.text=planeta.poblacion
        binding.tvTerrenoPla.text=planeta.terreno
    }

}
