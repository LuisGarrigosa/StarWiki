/**
 * ChatActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectostarwiki.adapters.MensajesAdapter
import com.example.proyectostarwiki.databinding.ActivityChatBinding
import com.example.proyectostarwiki.models.Mensajes
import com.example.proyectostarwiki.prefs.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    //Creación de variables que se inicializarán más tarde
    lateinit var binding: ActivityChatBinding
    lateinit var prefs: Prefs
    var email=""
    lateinit var adapter: MensajesAdapter
    var lista=ArrayList<Mensajes>()
    lateinit var db: FirebaseDatabase

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        email=prefs.getEmail().toString()
        db = FirebaseDatabase.getInstance("https://starwiki-b2e08-default-rtdb.europe-west1.firebasedatabase.app/")
        setRecycler()
        traerMensajes()
        setListeners()
    }

    /**
     * Función setListeners() que proporciona la funcionalidad a los botones del activity
     *
     */
    private fun setListeners() {
        binding.ivSend.setOnClickListener{
            enviarMensaje()
            it.ocultarTeclado()
        }
    }

    /**
     * Función ocultarTeclado() para ocultar el teclado al dejar de escribir
     *
     */
    private fun View.ocultarTeclado(){
        val inputmanager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmanager.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * Función enviarMensaje() para coger el mensaje escrito y mandarlo a la base de datos
     * de Firebase
     *
     */
    private fun enviarMensaje() {
        val texto=binding.tieChat.text.toString().trim()
        if (texto.isEmpty()) return
        val m = Mensajes(texto=texto, email=email)
        db.getReference("chat").child(m.fecha.toString()).setValue(m).addOnSuccessListener {
            traerMensajes()
            binding.tieChat.setText("")
        }
            .addOnFailureListener {

            }
    }

    /**
     * Función traerMensajes() para coger los mensajes de la base de datos de Firebase y
     * cargarlos en el recyclerView
     *
     */
    private fun traerMensajes() {
        db.getReference("chat").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        val mensaje = item.getValue(Mensajes::class.java)
                        if (mensaje!=null){
                            lista.add(mensaje)
                        }
                    }
                    lista.sortBy { mensaje -> mensaje.fecha }
                    adapter.lista=lista
                    adapter.notifyDataSetChanged()
                    binding.recChat.scrollToPosition(lista.size-1)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    /**
     * Función setRecycler() que se encarga de inicializar el
     * recyclerView y adaptarlo al formato que me interesa
     *
     */
    private fun setRecycler() {
        val layoutmanager = LinearLayoutManager(this)
        binding.recChat.layoutManager=layoutmanager
        adapter= MensajesAdapter(lista, email)
        binding.recChat.adapter=adapter
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
        menuInflater.inflate(R.menu.menuchat, menu)
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
        //Item para volver al activity menu
        when(item.itemId){
            R.id.itemVolver->{
                startActivity(Intent(this, MenuActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Sobreescritura de la función onRestart() que se encarga de realizar una acción al cargar
     * de nuevo el activity.
     *
     */
    override fun onRestart() {
        traerMensajes()
        super.onRestart()
    }
}