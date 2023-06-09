/**
 * NavesActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.adapters.NavesAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityNavesBinding
import com.example.proyectostarwiki.models.NavesData
import kotlinx.coroutines.launch

class NavesActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    private lateinit var binding: ActivityNavesBinding
    var listaBase = mutableListOf<NavesData>()
    lateinit var adapter: NavesAdapter
    lateinit var conexion: BaseDatos

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Inicializacion de variables y uso de funciones
        super.onCreate(savedInstanceState)
        binding = ActivityNavesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion=BaseDatos(this)
        cargarFondo()
        setRecycler()
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
        Glide.with(applicationContext).load(uri).into(binding.fondoNaves)
    }

    /**
     * Función setRecycler() que se encarga de inicializar el
     * recyclerView y adaptarlo al formato que me interesa
     *
     */
    private fun setRecycler() {
        traerNaves()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewNaves.layoutManager=layoutManager
        adapter= NavesAdapter(conexion.readNaves(), {onItemClick(it)})
        binding.recViewNaves.adapter=adapter
    }

    /**
     * Función onItemClick() que se encarga de recoger que item del recyclerView
     * se ha pulsado y mandar los datos del mismo al activity PilotosActivity
     *
     * @param nave nave seleccionada en el recyclerView
     *
     */
    private fun onItemClick(nave: NavesData) {
        val posicion = 200

        val i = Intent(this,PilotosActivity::class.java).apply {
            putExtra("NAVESEL", nave)
            putExtra("POSICION", posicion)
        }
        startActivity(i)
    }

    /**
     * Función traerNaves() que se encarga de coger los datos de las naves
     * através de la api de Star Wars y comprobar si la tabla naves tiene datos,
     * si es así no crea ninguno nuevo, si no tiene datos crea datos nuevos en la tabla
     *
     */
    private fun traerNaves() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getNaves()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readNaves()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createNaves(datos.results[datos.results.indexOf(i)])
                }
            }
        }
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
        menuInflater.inflate(R.menu.menusecciones, menu)
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
        when(item.itemId){
            R.id.itemBorrar->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla naves con sus pilotos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarNaves()
                    conexion.borrarPilotos()
                    Toast.makeText(this, "Naves y pilotos borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_LONG).show()
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

    /**
     * Sobreescritura de la función onResume() que se encarga de realizar una acción al cargar
     * de nuevo el activity.
     *
     */
    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}