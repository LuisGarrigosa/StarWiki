package com.example.proyectostarwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.NavesAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityNavesBinding
import com.example.proyectostarwiki.models.NavesData
import kotlinx.coroutines.launch

class NavesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavesBinding
    var lista = mutableListOf<NavesData>()
    var listaBase = mutableListOf<NavesData>()
    lateinit var adapter: NavesAdapter
    lateinit var conexion: BaseDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion=BaseDatos(this)
        setRecycler()
    }

    private fun setRecycler() {
        traerNaves()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recView.layoutManager=layoutManager
        adapter= NavesAdapter(lista, {onItemClick(it)})
        binding.recView.adapter=adapter
    }

    private fun onItemClick(nave: NavesData) {
        val i = Intent(this,PilotosActivity::class.java)
        startActivity(i)
    }

    private fun traerNaves() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getNaves()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readNaves()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createNaves(datos.results[datos.results.indexOf(i)])
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}