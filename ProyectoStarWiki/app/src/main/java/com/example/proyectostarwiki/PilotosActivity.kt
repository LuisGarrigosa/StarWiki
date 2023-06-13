/**
 * PilotosActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.adapters.PilotosAdapter
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityPilotosBinding
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData

class PilotosActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityPilotosBinding
    lateinit var adapter: PilotosAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<PilotosData>()
    lateinit var naveSeleccionada: NavesData

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cargarFondo()
        cogerLista()
        setRecycler()
        setListeners()
        mandarDatos()
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
        Glide.with(applicationContext).load(uri).into(binding.fondoPilotos)
    }

    /**
     * Función mandarDatos() que carga los datos de la nave seleccionada
     * en la etiqueta de la nave que ha sido seleccionada
     *
     */
    private fun mandarDatos() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData
        binding.tvNaveSel.text=naveSeleccionada.nombre
    }

    /**
     * Función setListeners() que proporciona la funcionalidad a los botones del activity
     *
     */
    private fun setListeners() {
        binding.btnAdd.setOnClickListener{
            val i = Intent(this,GestionPilotosActivity::class.java).apply {
                putExtra("NAVESEL", naveSeleccionada)
            }
            startActivity(i)
        }
    }

    /**
     * Función setRecycler() que se encarga de inicializar el
     * recyclerView y adaptarlo al formato que me interesa
     *
     */
    private fun setRecycler() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData

        listaBase=conexion.readPilotosSeleccionados(naveSeleccionada.nombre)
        val layoutManager = GridLayoutManager(this, 1)
        binding.recPilotos.layoutManager = layoutManager
        adapter = PilotosAdapter(listaBase)
        binding.recPilotos.adapter = adapter
    }

    /**
     * Función cogerLista() que comprueba que hay pilotos creados asignados
     * a la nave seleccionada, si es así la etiqueta que dice que esta vacía la nave
     * se hace invisible, sino se hace visible
     *
     */
    private fun cogerLista() {
        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData

        listaBase=conexion.readPilotosSeleccionados(naveSeleccionada.nombre)
        if (listaBase.size>0){
            binding.tvVacio.visibility = View.INVISIBLE
        }else{
            binding.tvVacio.visibility = View.VISIBLE
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
            //Item que borra la tabla pilotos
            R.id.itemBorrar->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla pilotos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarPilotos()
                    Toast.makeText(this, "Pilotos borrados, añada de nuevo pilotos a la base de datos...", Toast.LENGTH_LONG).show()
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
            //Item para volver al activity naves
            R.id.itemVolver->{
                val i = Intent(this,NavesActivity::class.java)

                startActivity(i)
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
        cogerLista()
        setRecycler()
    }
}