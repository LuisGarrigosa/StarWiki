/**
 * MenuActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.databinding.ActivityMenuBinding
import com.example.proyectostarwiki.prefs.Prefs
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectostarwiki.basedatos.BaseDatos

class MenuActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityMenuBinding
    lateinit var prefs:Prefs
    lateinit var conexion: BaseDatos

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Inicializacion de variables y uso de funciones
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        conexion = BaseDatos(this)
        cargarFondo()
        setListeners()
    }

    /**
     * Función cargarFondo() que carga el fondo gif de la aplicación
     * en el imageView que tiene la aplicación como fondo, usando una url
     * para acceder al gif a través de internet
     *
     */
    private fun cargarFondo() {
        val urlGif = "https://i.gifer.com/IrM.gif"
        val uri = Uri.parse(urlGif)
        Glide.with(applicationContext).load(uri).into(binding.fondoMenu)
    }

    /**
     * Función setListeners() que proporciona la funcionalidad a los botones del activity
     *
     */
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

        binding.btnChat.setOnClickListener {
            irChat()
        }
    }

    /**
     * Función irChat() que carga el activity ChatActivity
     *
     */
    private fun irChat() {
        startActivity(Intent(this,ChatActivity::class.java))
    }

    /**
     * Función irPeliculas() que carga el activity PeliculasActivity
     *
     */
    private fun irPeliculas() {
        startActivity(Intent(this, PeliculasActivity::class.java))
    }

    /**
     * Función irPlanetas() que carga el activity PlanetasActivity
     *
     */
    private fun irPlanetas() {
        startActivity(Intent(this, PlanetasActivity::class.java))
    }

    /**
     * Función irVehiculos() que carga el activity VehiculosActivity
     *
     */
    private fun irVehiculos() {
        startActivity(Intent(this,VehiculosActivity::class.java))
    }

    /**
     * Función irNaves() que carga el activity NavesActivity
     *
     */
    private fun irNaves() {
        startActivity(Intent(this,NavesActivity::class.java))
    }

    /**
     * Sobreescritura de la función onCreateOptionsMenu para inflar el menú que he creado
     * en el activity
     *
     * @param menu menu que vamos a utilizar
     * @return menu seleccionado
     *
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Sobreescritura de la función onOptionsItemSelected para indicar que función realizará cada
     * item del menú
     *
     * @param item menuItem que vamos a utilizar
     * @return item seleccionado con su funcionalidad
     *
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Selección del menuItem
        when(item.itemId){
            //Item para cerrar sesión
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
                    val i = Intent(this,MainActivity::class.java)
                    startActivity(i)
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

            //Item para cerrar la aplicación
            R.id.itemSalir->{
                finishAffinity()
            }

            //Item para borrar las tablas de la base de datos
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
                    val i = Intent(this,MainActivity::class.java)
                    startActivity(i)
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