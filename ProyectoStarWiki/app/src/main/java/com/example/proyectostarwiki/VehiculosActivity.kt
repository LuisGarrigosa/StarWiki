/**
 * VehiculosActivity.kt
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
import com.example.proyectostarwiki.adapters.VehiculosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityVehiculosBinding
import com.example.proyectostarwiki.models.VehiculosData
import kotlinx.coroutines.launch

class VehiculosActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityVehiculosBinding
    lateinit var adapter: VehiculosAdapter
    lateinit var conexion: BaseDatos
    var listaBase = mutableListOf<VehiculosData>()

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Inicializacion de variables y uso de funciones
        super.onCreate(savedInstanceState)
        binding = ActivityVehiculosBinding.inflate(layoutInflater)
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
        traerVehiculos()
        val layoutManager = GridLayoutManager(this, 1)
        binding.recViewVeh.layoutManager=layoutManager
        adapter= VehiculosAdapter(conexion.readVehiculos(), {onItemClick(it)})
        binding.recViewVeh.adapter=adapter
    }

    /**
     * Aun por ver
     *
     */
    private fun onItemClick(vehiculo: VehiculosData) {
        val i = Intent(this,PilotosActivity::class.java).apply {
            putExtra("VEHISEL", vehiculo)
        }
        startActivity(i)
    }

    /**
     * Función traerVehiculos() que se encarga de coger los datos de los vehiculos
     * através de la api de Star Wars y comprobar si la tabla vehiculos tiene datos,
     * si es así no crea ninguno nuevo, si no tiene datos crea datos nuevos en la tabla
     *
     */
    private fun traerVehiculos() {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getVehiculos()
            adapter.lista=datos.results.toMutableList()
            adapter.notifyDataSetChanged()
            listaBase=conexion.readVehiculos()
            if (listaBase.size==0){
                for (i in datos.results){
                    conexion.createVehiculos(datos.results[datos.results.indexOf(i)])
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
        Glide.with(applicationContext).load(uri).into(binding.fondoVehiculos)
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
            //Item para borra la tabla vehiculos
            R.id.itemBorrar->{
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("AVISO!!!")
                alertDialogBuilder.setMessage("¿Seguro quiere borrar la tabla vehiculos?")

                // Botón positivo
                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción al hacer clic en el botón positivo
                    conexion.borrarVehiculos()
                    Toast.makeText(this, "Vehiculos borrados, vuelva a abrir la sección para volver a cargarlos...", Toast.LENGTH_LONG).show()
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