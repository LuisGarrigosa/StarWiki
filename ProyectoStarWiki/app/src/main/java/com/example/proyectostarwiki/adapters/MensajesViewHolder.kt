package com.example.proyectostarwiki.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectostarwiki.R
import com.example.proyectostarwiki.databinding.MensajeLayoutBinding
import com.example.proyectostarwiki.models.Mensajes
import java.text.SimpleDateFormat
import java.util.*

class MensajesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = MensajeLayoutBinding.bind(v)

    fun render(m: Mensajes, email: String){
        binding.tvMensaje.text=m.texto
        binding.tvEmail.text=m.email
        binding.tvHora.text=convertLongToDate(m.fecha)
        if (m.email==email){
            binding.cLayout.setBackgroundColor(ContextCompat.getColor(binding.cLayout.context, R.color.cchat))
        }else{
            binding.cLayout.setBackgroundColor(ContextCompat.getColor(binding.cLayout.context, R.color.white))
        }
    }

    private fun convertLongToDate(time:Long): String{
        val date = Date(time)
        val format = SimpleDateFormat("h:m:ss a dd/MM/yyyy")
        return format.format(date)
    }

}
