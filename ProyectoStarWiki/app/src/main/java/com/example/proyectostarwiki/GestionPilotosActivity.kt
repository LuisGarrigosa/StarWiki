package com.example.proyectostarwiki

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.PilotosAdapter
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
        setRecycler()
        setListeners()

    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 4)
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            binding.recSeleccionPilotos.layoutManager=layoutManager
            adapter=SeleccionPilotosAdapter(datos.results.toMutableList(), {addItem(it)})
            binding.recSeleccionPilotos.adapter=adapter
        }

        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData
    }

    private fun addItem(position: Int) {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            val pilotoBuscado = (datos.results.get(position+1))

            val datosRecogidos = intent.extras
            naveSeleccionada = datosRecogidos?.getSerializable("NAVESEL") as NavesData

            listaBase = conexion.readPilotos()
            var contador = 0

            for (piloto in listaBase){
                if (piloto.nombre == pilotoBuscado.nombre){
                    if (piloto.nombreNave?.isNotEmpty() == true){
                        Toast.makeText(this@GestionPilotosActivity, "El piloto ya tiene una nave asignada", Toast.LENGTH_SHORT).show()
                    } else {
                        conexion.createPilotos(pilotoBuscado, naveSeleccionada.nombre)
                        Toast.makeText(this@GestionPilotosActivity, "El piloto se ha añadido", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                            putExtra("NAVESEL", naveSeleccionada)
                        }
                        startActivity(i)
                    }
                } else {
                    conexion.createPilotos(pilotoBuscado, naveSeleccionada.nombre)
                    Toast.makeText(this@GestionPilotosActivity, "El piloto se ha añadido", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                        putExtra("NAVESEL", naveSeleccionada)
                    }
                    startActivity(i)
                }
            }
        }
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