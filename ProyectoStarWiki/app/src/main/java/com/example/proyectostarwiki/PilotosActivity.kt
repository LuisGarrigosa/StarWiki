package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.adapters.PilotosAdapter
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPilotosBinding
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData
import com.google.firebase.auth.FirebaseAuth

class PilotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityPilotosBinding
    lateinit var adapter: PilotosAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<PilotosData>()
    lateinit var naveSeleccionada: NavesData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cargarFondo()
        cogerLista()
        setRecycler()
        setListeners()
        mandarDatos()
    }

    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoPilotos)
    }

    private fun mandarDatos() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData
        binding.tvNaveSel.text=naveSeleccionada.nombre
    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener{
            val i = Intent(this,GestionPilotosActivity::class.java).apply {
                putExtra("NAVESEL", naveSeleccionada)
            }
            startActivity(i)
        }
    }

    private fun setRecycler() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData

        val layoutManager = GridLayoutManager(this, 3)
        adapter = PilotosAdapter(listaBase)
        binding.recPilotos.layoutManager = layoutManager
        binding.recPilotos.adapter = adapter
    }

    private fun actualizarListaPilotos() {
        listaBase.clear()
        listaBase.addAll(conexion.readPilotosSeleccionados(naveSeleccionada.nombre))
        adapter.notifyDataSetChanged()

        if (listaBase.size > 0) {
            binding.tvVacio.visibility = View.INVISIBLE
        } else {
            binding.tvVacio.visibility = View.VISIBLE
        }
    }

    private fun cogerLista() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData

        listaBase=conexion.readPilotosSeleccionados(naveSeleccionada.nombre)
        if (listaBase.size>0){
            binding.tvVacio.visibility = View.INVISIBLE
        }else{
            binding.tvVacio.visibility = View.VISIBLE
        }

        setRecycler()
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
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla pilotos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarPilotos()
                    Toast.makeText(this, "Pilotos borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_LONG).show()
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
        actualizarListaPilotos()
    }
}