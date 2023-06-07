package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
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
        cargarFondo()
        setRecycler()
    }

    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoNaves)
    }

    private fun setRecycler() {
        traerNaves()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewNaves.layoutManager=layoutManager
        adapter= NavesAdapter(lista, {onItemClick(it)})
        binding.recViewNaves.adapter=adapter
    }

    private fun onItemClick(nave: NavesData) {
        val i = Intent(this,PilotosActivity::class.java).apply {
            putExtra("NAVESEL", nave)
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menusecciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemBorrar->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla naves con sus pilotos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarNaves()
                    conexion.borrarPilotos()
                    Toast.makeText(this, "Naves y pilotos borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_LONG).show()
                    finish()
                }

                // Botón negativo
                alertDialogBuilder.setNegativeButton("Cancelar") { dialog, which ->
                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
                }

                // Crear el diálogo
                val alertDialog = alertDialogBuilder.create()

                // Mostrar el diálogo
                alertDialog.show()
            }

            R.id.itemVolver->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}