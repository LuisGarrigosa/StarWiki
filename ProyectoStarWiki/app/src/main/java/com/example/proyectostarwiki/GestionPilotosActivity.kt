package com.example.proyectostarwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import com.example.proyectostarwiki.adapters.PilotosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.databinding.ActivityGestionPilotosBinding
import com.example.proyectostarwiki.models.PilotosData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class GestionPilotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityGestionPilotosBinding
    var lista = mutableListOf<PilotosData>()
    lateinit var adapter: PilotosAdapter
    lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=FirebaseDatabase.getInstance("https://starwiki-70794-default-rtdb.europe-west1.firebasedatabase.app/")
        traerPilotos()
        setListeners()
    }

    private fun traerPilotos() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            adapter.lista = datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            adapter.lista=lista

            //var databaseReference = db.getReference().child("naves").setValue(datos.results)
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