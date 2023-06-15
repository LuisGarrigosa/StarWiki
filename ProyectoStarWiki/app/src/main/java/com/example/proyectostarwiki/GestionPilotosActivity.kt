/**
 * GestionPilotosActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectostarwiki.adapters.SeleccionPilotosAdapter
import com.example.proyectostarwiki.apiprovider.apiClient
import com.example.proyectostarwiki.basedatos.BaseDatos
import com.example.proyectostarwiki.databinding.ActivityGestionPilotosBinding
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData
import kotlinx.coroutines.launch

class GestionPilotosActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityGestionPilotosBinding
    var listaBase = mutableListOf<PilotosData>()
    lateinit var adapter: SeleccionPilotosAdapter
    lateinit var conexion: BaseDatos
    lateinit var naveSeleccionada: NavesData

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionPilotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion= BaseDatos(this)
        setRecycler()
        setListeners()

    }

    /**
     * Función setRecycler() que se encarga de inicializar el
     * recyclerView y adaptarlo al formato que me interesa
     *
     */
    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 4)
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            binding.recSeleccionPilotos.layoutManager=layoutManager
            adapter=SeleccionPilotosAdapter(datos.results.toMutableList(), {addItem(it)})
            binding.recSeleccionPilotos.adapter=adapter
        }

        val datos = intent.extras
        naveSeleccionada = datos?.getSerializable("NAVESEL") as NavesData
    }

    /**
     * Función addItem() que le da funcionalidad al botón para añadir cada piloto dentro del
     * recyclerView, se crea un piloto con los datos del piloto seleccionado y se comprueba si
     * ya ha sido seleccionado en alguna nave, si es así se avisa de que el piloto ya está asignado
     * a una nave, si no es así se añade el piloto a la nave y se añade a la base de datos
     *
     */
    private fun addItem(position: Int) {
        lifecycleScope.launch() {
            val datos = apiClient.apiClient.getPilotos()
            val pilotoBuscado = (datos.results.get(position))

            val datosRecogidos = intent.extras
            naveSeleccionada = datosRecogidos?.getSerializable("NAVESEL") as NavesData

            listaBase = conexion.readPilotos()
            var creado = false

            if (listaBase.size > 0){
                for (piloto in listaBase){
                    if (piloto.nombre == pilotoBuscado.nombre){
                        if (piloto.nombreNave?.isNotEmpty() == true){
                            Toast.makeText(this@GestionPilotosActivity, "El piloto ya tiene una nave asignada", Toast.LENGTH_SHORT).show()
                            creado=true
                            val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                                putExtra("NAVESEL", naveSeleccionada)
                            }
                            startActivity(i)
                            break
                        } else {
                            conexion.createPilotos(pilotoBuscado, naveSeleccionada.nombre)
                            Toast.makeText(this@GestionPilotosActivity, "El piloto se ha añadido", Toast.LENGTH_SHORT).show()
                            creado=true
                            val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                                putExtra("NAVESEL", naveSeleccionada)
                            }
                            startActivity(i)
                            break
                        }
                    }
                }

                if (!creado){
                    conexion.createPilotos(pilotoBuscado, naveSeleccionada.nombre)
                    Toast.makeText(this@GestionPilotosActivity, "El piloto se ha añadido", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                        putExtra("NAVESEL", naveSeleccionada)
                    }
                    startActivity(i)
                }
            } else {
                conexion.createPilotos(pilotoBuscado, naveSeleccionada.nombre)
                Toast.makeText(this@GestionPilotosActivity, "El piloto se ha añadido", Toast.LENGTH_SHORT).show()
                val i = Intent(this@GestionPilotosActivity, PilotosActivity::class.java).apply {
                    putExtra("NAVESEL", naveSeleccionada)
                }
                startActivity(i)
            }
        }
    }

    /**
     * Función setListeners() que proporciona la funcionalidad a los botones del activity
     *
     */
    private fun setListeners() {

        binding.btnCancelar.setOnClickListener {
            val i = Intent(this,PilotosActivity::class.java).apply {
                putExtra("NAVESEL", naveSeleccionada)
            }
            startActivity(i)
        }
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