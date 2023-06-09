package com.example.proyectostarwiki

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.SeleccionPilotosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityGestionPilotosBinding
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData
import kotlinx.coroutines.launch

class GestionPilotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityGestionPilotosBinding
    var listaBase = mutableListOf<PilotosData>()
    lateinit var adapter: SeleccionPilotosAdapter
    lateinit var conexion: BaseDatos
    lateinit var naveSeleccionada: NavesData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion= BaseDatos(this)
        traerPilotos()
        setRecycler()
        setListeners()

    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 4)
        binding.recSeleccionPilotos.layoutManager=layoutManager
        adapter=SeleccionPilotosAdapter(listaBase, {addItem(it)})
        binding.recSeleccionPilotos.adapter=adapter
    }

    private fun addItem(position: Int) {
        val i = Intent(this,PilotosActivity::class.java).apply {
            putExtra("NAVESEL", naveSeleccionada)
            putExtra("POSICION",position+1)
        }
        startActivity(i)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun traerPilotos() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            adapter.lista = datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase = conexion.readPilotos()
            if (listaBase.isEmpty()){
                for (i in datos.results){
                    conexion.createPilotos(datos.results[datos.results.indexOf(i)])
                }
            }
        }

        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData
    }

    private fun setListeners() {

        binding.btnCancelar.setOnClickListener {
            val i = Intent(this,PilotosActivity::class.java).apply {
                putExtra("NAVESEL", naveSeleccionada)
            }
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}