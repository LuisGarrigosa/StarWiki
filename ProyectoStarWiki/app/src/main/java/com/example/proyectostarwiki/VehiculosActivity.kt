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
import com.example.proyectostarwiki.adapters.VehiculosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityVehiculosBinding
import com.example.proyectostarwiki.models.VehiculosData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class VehiculosActivity : AppCompatActivity() {
    lateinit var binding: ActivityVehiculosBinding
    lateinit var adapter: VehiculosAdapter
    lateinit var conexion: BaseDatos
    var lista = mutableListOf<VehiculosData>()
    var listaBase = mutableListOf<VehiculosData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehiculosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cargarFondo()
        setRecycler()
    }

    private fun setRecycler() {
        traerVehiculos()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewVeh.layoutManager=layoutManager
        adapter= VehiculosAdapter(lista)
        binding.recViewVeh.adapter=adapter
    }

    private fun traerVehiculos() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getVehiculos()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readVehiculos()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createVehiculos(datos.results[datos.results.indexOf(i)])
                }
            }
        }
    }

    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoVehiculos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menusecciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemBorrar->{
                conexion.borrarVehiculos()
                Toast.makeText(this, "Vehiculos borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_SHORT).show()
                finish()
            }

            R.id.itemVolver->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}