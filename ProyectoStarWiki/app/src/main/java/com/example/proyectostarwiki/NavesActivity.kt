package com.example.proyectostarwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.NavesAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.databinding.ActivityNavesBinding
import com.example.proyectostarwiki.models.NavesData
import kotlinx.coroutines.launch

class NavesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavesBinding
    var lista = mutableListOf<NavesData>()
    lateinit var adapter: NavesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecycler()
    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 1)
        binding.recView.layoutManager=layoutManager
        adapter= NavesAdapter(lista)
        binding.recView.adapter=adapter
        traerNaves()
    }

    private fun traerNaves() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getNaves()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
        }
    }
}