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
import com.example.proyectostarwiki.adapters.PeliculasAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPeliculasBinding
import com.example.proyectostarwiki.models.PeliculasData
import kotlinx.coroutines.launch

class PeliculasActivity : AppCompatActivity() {
    lateinit var binding: ActivityPeliculasBinding
    lateinit var adapter: PeliculasAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<PeliculasData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion= BaseDatos(this)
        cargarFondo()
        setRecycler()
    }

    private fun setRecycler() {
        traerPelis()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewPelis.layoutManager=layoutManager
        adapter= PeliculasAdapter(conexion.readPeliculas())
        binding.recViewPelis.adapter=adapter
    }

    private fun traerPelis() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPeliculas()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readPeliculas()
            if (listaBase.isEmpty()){
                for (i in datos.results){
                    conexion.createPeliculas(datos.results[datos.results.indexOf(i)])
                }
            }
        }
    }
    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoPelis)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menusecciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemBorrar->{
                conexion.borrarPeliculas()
                Toast.makeText(this, "Peliculas borradas, vuelva a abrir la sección para volver a cargarlas...", Toast.LENGTH_SHORT).show()
                finish()
            }

            R.id.itemVolver->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}