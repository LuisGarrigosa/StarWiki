/**
 * PlanetasActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

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
import com.example.proyectostarwiki.adapters.PlanetasAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPlanetasBinding
import com.example.proyectostarwiki.models.PlanetasData
import kotlinx.coroutines.launch

class PlanetasActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityPlanetasBinding
    lateinit var adapter: PlanetasAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<PlanetasData>()

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Inicializacion de variables y uso de funciones
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cargarFondo()
        setRecycler()
    }

    /**
     * Función setRecycler() que se encarga de inicializar el
     * recyclerView y adaptarlo al formato que me interesa
     *
     */
    private fun setRecycler() {
        traerPlanetas()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewPlanetas.layoutManager=layoutManager
        adapter= PlanetasAdapter(conexion.readPlanetas())
        binding.recViewPlanetas.adapter=adapter
    }

    /**
     * Función traerPlanetas() que se encarga de coger los datos de los planetas
     * através de la api de Star Wars y comprobar si la tabla planetas tiene datos,
     * si es así no crea ninguno nuevo, si no tiene datos crea datos nuevos en la tabla
     *
     */
    private fun traerPlanetas() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPlanetas()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readPlanetas()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createPlanetas(datos.results[datos.results.indexOf(i)])
                }
            }
        }
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
        Glide.with(applicationContext).load(uri).into(binding.fondoPlanetas)
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
        //Selección del menuItem
        when(item.itemId){
            //Item para borrar la tabla planetas
            R.id.itemBorrar->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla planetas?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarPlanetas()
                    Toast.makeText(this, "Planetas borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_LONG).show()
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
            //Item para volver al activity anterior
            R.id.itemVolver->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}