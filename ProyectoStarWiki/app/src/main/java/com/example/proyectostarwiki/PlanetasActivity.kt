package com.example.proyectostarwiki

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.adapters.PlanetasAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPlanetasBinding
import com.example.proyectostarwiki.models.PlanetasData
import kotlinx.coroutines.launch

class PlanetasActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlanetasBinding
    lateinit var adapter: PlanetasAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<PlanetasData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cargarFondo()
        setRecycler()
    }

    private fun setRecycler() {
        traerPlanetas()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewPlanetas.layoutManager=layoutManager
        adapter= PlanetasAdapter(conexion.readPlanetas())
        binding.recViewPlanetas.adapter=adapter
    }

    private fun traerPlanetas() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPlanetas()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readPlanetas()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createPlanetas(datos.results[datos.results.indexOf(i)])
                }
            }
        }
    }

    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoPlanetas)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menusecciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemBorrar->{
                conexion.borrarPlanetas()
                Toast.makeText(this, "Planetas borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_SHORT).show()
                finish()
            }

            R.id.itemVolver->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}