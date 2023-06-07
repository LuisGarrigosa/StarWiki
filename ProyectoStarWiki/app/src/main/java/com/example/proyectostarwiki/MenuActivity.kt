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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectostarwiki.basedatos.BaseDatos

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var prefs:Prefs
    lateinit var conexion: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        conexion = BaseDatos(this)
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

        binding.btnPeliculas.setOnClickListener {
            irPeliculas()
        }
    }

    private fun irPeliculas() {
        startActivity(Intent(this, PeliculasActivity::class.java))
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
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro que quiere cerrar sesión?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    FirebaseAuth.getInstance().signOut()
                    prefs.borrarTodo()
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
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

            R.id.itemSalir->{
                finishAffinity()
            }

            R.id.itemBorrarTodo->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar toda la base datos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    FirebaseAuth.getInstance().signOut()
                    prefs.borrarTodo()
                    conexion.borrarTodo()
                    Toast.makeText(this, "Base de datos borrada, vuelva a iniciar sesión...", Toast.LENGTH_LONG).show()
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
        }

        return super.onOptionsItemSelected(item)
    }
}