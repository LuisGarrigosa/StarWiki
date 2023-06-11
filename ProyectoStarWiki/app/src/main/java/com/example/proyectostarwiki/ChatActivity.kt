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

    lateinit var binding: ActivityChatBinding
    lateinit var prefs: Prefs
    var email=""
    lateinit var adapter: MensajesAdapter
    var lista=ArrayList<Mensajes>()
    lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        email=prefs.getEmail().toString()
        db = FirebaseDatabase.getInstance("https://starwiki-70794-default-rtdb.europe-west1.firebasedatabase.app/")
        setRecycler()
        traerMensajes()
        setListeners()
    }

    private fun setListeners() {
        binding.ivSend.setOnClickListener{
            enviarMensaje()
            it.ocultarTeclado()
        }
    }

    private fun View.ocultarTeclado(){
        val inputmanager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmanager.hideSoftInputFromWindow(windowToken, 0)
    }

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

    private fun setRecycler() {
        val layoutmanager = LinearLayoutManager(this)
        binding.recChat.layoutManager=layoutmanager
        adapter= MensajesAdapter(lista, email)
        binding.recChat.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuchat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemVolver->{
                startActivity(Intent(this, MenuActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        traerMensajes()
        super.onRestart()
    }
}