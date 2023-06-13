/**
 * PilotosViewHolder.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.databinding.PilotosLayoutBinding
import com.example.proyectostarwiki.models.PilotosData

class PilotosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = PilotosLayoutBinding.bind(v)

    fun render(piloto: PilotosData){
        binding.tvNombrePiloto.text=piloto.nombre
        binding.tvGeneroPiloto.text=piloto.genero
        binding.tvAlturaPiloto.text=piloto.altura
        binding.tvPesoPiloto.text=piloto.peso

        val nave = piloto.nombreNave

        when(nave){
            "CR90 corvette"->{
                binding.ivNave.setImageResource(R.drawable.cr90)
            }

            "Star Destroyer"->{
                binding.ivNave.setImageResource(R.drawable.destroyer)
            }

            "Sentinel-class landing craft"->{
                binding.ivNave.setImageResource(R.drawable.sentinel)
            }

            "Death Star"->{
                binding.ivNave.setImageResource(R.drawable.deathstar)
            }

            "Millennium Falcon"->{
                binding.ivNave.setImageResource(R.drawable.falcon)
            }

            "Y-wing"->{
                binding.ivNave.setImageResource(R.drawable.ywing)
            }

            "X-wing"->{
                binding.ivNave.setImageResource(R.drawable.xwing)
            }

            "TIE Advanced x1"->{
                binding.ivNave.setImageResource(R.drawable.tie)
            }

            "Executor"->{
                binding.ivNave.setImageResource(R.drawable.executor)
            }

            "Rebel transport"->{
                binding.ivNave.setImageResource(R.drawable.rebel)
            }

            else->{
                binding.ivNave.setImageResource(R.drawable.imagenfondoinicio)
            }
        }

    }
}
