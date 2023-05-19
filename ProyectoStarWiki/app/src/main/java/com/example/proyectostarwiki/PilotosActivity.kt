package com.example.proyectostarwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.NavesAdapter
import com.example.proyectostarwiki.adapters.PilotosAdapter
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPilotosBinding
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData

class PilotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityPilotosBinding
    lateinit var adapter: PilotosAdapter
    lateinit var conexion: BaseDatos
    var lista = mutableListOf<PilotosData>()
    var listaBase = mutableListOf<PilotosData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cogerLista()
        setRecycler()
        setListeners()
    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener{
            startActivity(Intent(this, GestionPilotosActivity::class.java))
        }
    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 3)
        binding.recPilotos.layoutManager=layoutManager
        adapter= PilotosAdapter(lista)
        binding.recPilotos.adapter=adapter
    }

    private fun cogerLista() {
        listaBase=conexion.readPilotos()
        if (listaBase.size>0){
            binding.tvVacio.visibility = View.INVISIBLE
        }else{
            binding.tvVacio.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        cogerLista()
        setRecycler()
    }
}