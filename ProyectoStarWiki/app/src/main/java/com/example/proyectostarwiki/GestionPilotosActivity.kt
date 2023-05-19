package com.example.proyectostarwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.proyectostarwiki.adapters.PilotosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityGestionPilotosBinding
import com.example.proyectostarwiki.models.PilotosData
import kotlinx.coroutines.launch

class GestionPilotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityGestionPilotosBinding
    var lista = mutableListOf<PilotosData>()
    var listaBase = mutableListOf<PilotosData>()
    lateinit var adapter: PilotosAdapter
    lateinit var conexion: BaseDatos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion= BaseDatos(this)
        adapter= PilotosAdapter(lista)
        traerPilotos()
        rellenarSpinner()
        setListeners()

    }

    private fun rellenarSpinner() {
        var pilotos= ArrayList<String>()
        listaBase = conexion.readPilotos()

        for (i in listaBase){
            pilotos.add(listaBase[listaBase.indexOf(i)].nombre)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pilotos)
        binding.spinnerPilotos.adapter= adapter
    }

    private fun traerPilotos() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            adapter.lista = datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            //adapter.lista=lista
            listaBase = conexion.readPilotos()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createPilotos(datos.results[datos.results.indexOf(i)])
                }
            }
        }
    }

    private fun setListeners() {
        binding.spinnerPilotos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.btnCancelar.setOnClickListener {
            finishAffinity()
        }

        binding.btnGuardar.setOnClickListener {

        }
    }
}