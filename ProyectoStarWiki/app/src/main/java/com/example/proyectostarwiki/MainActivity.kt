package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.proyectostarwiki.databinding.ActivityMainBinding
import com.example.proyectostarwiki.prefs.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

        lateinit var binding: ActivityMainBinding
        lateinit var prefs: Prefs

        private val responseLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode== RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try{
                    val cuenta = task.getResult(ApiException::class.java)
                    if (cuenta!=null){
                        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credenciales).addOnCompleteListener {
                            if (it.isSuccessful){
                                prefs.setEmail(cuenta.email?:"")
                                irMenu()
                            } else {
                                println(it.result.toString())
                            }
                        }
                    }
                }catch (e:ApiException){
                    println(e.message)
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        cargarFondo()
        comprobarLogin()
        setListeners()
    }

    private fun cargarFondo() {
        /*
        if(NetworkUtils.isInternetReachable(this)){
            val urlGif = "https://i.gifer.com/IrM.gif"
            val uri = Uri.parse(urlGif)
            Glide.with(applicationContext).load(uri).into(binding.fondo)
        } else {
            //Fondo nuevo
        }
         */
        var idVideo = R.raw.stars
        var rutaVideo="android.resource://"+packageName+"/$idVideo"
        var uri = Uri.parse(rutaVideo)
        try {
            binding.fondo.setVideoURI(uri)
            binding.fondo.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun irMenu() {
        startActivity(Intent(this,MenuActivity::class.java))
    }

    private fun setListeners() {
        binding.botonGoogle.setOnClickListener {
            login()
        }
        binding.fondo.setOnPreparedListener{
            it.isLooping=true
        }
    }

    private fun login() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("902621700012-kl0fioie77dgdjormue63u09ggtpash9.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        googleClient.signOut()

        responseLauncher.launch(googleClient.signInIntent)
    }

    private fun comprobarLogin() {
        val email = prefs.getEmail()
        if (email!=null){
            irMenu()
        }
    }
}