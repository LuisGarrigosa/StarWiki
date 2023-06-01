package com.example.proyectostarwiki

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.databinding.ActivityMenuBinding
import com.example.proyectostarwiki.prefs.Prefs
import com.google.firebase.auth.FirebaseAuth
import android.util.DisplayMetrics




class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var prefs:Prefs
    lateinit var metrics: DisplayMetrics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        metrics = DisplayMetrics()
        prefs= Prefs(this)
        cargarFondo()
        setListeners()
        ponerColor()
    }

    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoMenu)
    }

    private fun ponerColor() {

    }

    private fun setListeners() {
        binding.btnNaves.setOnClickListener{
            irNaves()
        }

        binding.btnVehiculos.setOnClickListener {
            irVehiculos()
        }

        binding.btnPlanetas.setOnClickListener {
            irPlanetas()
        }
    }

    private fun irPlanetas() {
        startActivity(Intent(this, PlanetasActivity::class.java))
    }

    private fun irVehiculos() {
        startActivity(Intent(this,VehiculosActivity::class.java))
    }

    private fun irNaves() {
        startActivity(Intent(this,NavesActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemSesion->{
                FirebaseAuth.getInstance().signOut()
                prefs.borrarTodo()
                finish()
            }

            R.id.itemSalir->{
                finishAffinity()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}