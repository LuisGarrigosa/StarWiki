/**
 * MainActivity.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        //Creacion de variables que se inicializaran despues
        lateinit var binding: ActivityMainBinding
        lateinit var prefs: Prefs

        //Variable que controla el login de Google
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

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicializacion de variables y uso de funciones
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs= Prefs(this)
        cargarFondo()
        comprobarLogin()
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
        Glide.with(applicationContext).load(uri).into(binding.fondo)
    }

    /**
     * Función irMenu() que carga el activity MenuActivity
     *
     */
    private fun irMenu() {
        startActivity(Intent(this,MenuActivity::class.java))
    }

    /**
     * Función setListeners() que proporciona la funcionalidad a los botones del activity
     *
     */
    private fun setListeners() {
        binding.botonGoogle.setOnClickListener {
            login()
        }
    }

    /**
     * Función login() que se engarga de hacer el login de Google, usando la autenticación
     * de mi proyecto de Firebase
     *
     */
    private fun login() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("128180867298-fv8in92tatvf3472mde62s6ivj05uk4j.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        googleClient.signOut()

        responseLauncher.launch(googleClient.signInIntent)
    }

    /**
     * Función comprobarLogin() que se encarga de mirar si se había iniciado sesión antes
     * pero no se habia cerrado la sesión aún, por lo tanto automáticamente iniciará sesión
     * con la última cuenta que se dejó abierta la aplicación
     *
     */
    private fun comprobarLogin() {
        val email = prefs.getEmail()
        if (email!=null){
            irMenu()
        }
    }
}