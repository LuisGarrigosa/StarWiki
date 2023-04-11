package com.example.proyectostarwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.proyectostarwiki.databinding.ActivityMenuBinding
import com.example.proyectostarwiki.prefs.Prefs
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var prefs:Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        setListeners()
    }

    private fun setListeners() {
        binding.btnNaves.setOnClickListener{
            irNaves()
        }
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