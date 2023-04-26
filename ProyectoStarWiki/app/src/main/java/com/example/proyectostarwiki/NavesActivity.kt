package com.example.proyectostarwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.NavesAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.databinding.ActivityNavesBinding
import com.example.proyectostarwiki.models.NavesData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class NavesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavesBinding
    var lista = mutableListOf<NavesData>()
    lateinit var adapter: NavesAdapter
    lateinit var db: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=FirebaseDatabase.getInstance("https://starwiki-70794-default-rtdb.europe-west1.firebasedatabase.app/")
        setRecycler()
    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 1)
        binding.recView.layoutManager=layoutManager
        adapter= NavesAdapter(lista, {onItemClick(it)})
        binding.recView.adapter=adapter
        traerNaves()
    }

    private fun onItemClick(nave: NavesData) {
        val i = Intent(this,PilotosActivity::class.java).apply {
            putExtra("NAVE", nave)
        }
        startActivity(i)
    }

    private fun traerNaves() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getNaves()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            var databaseReference = db.getReference().child("naves").setValue(datos.results)
        }
    }

}