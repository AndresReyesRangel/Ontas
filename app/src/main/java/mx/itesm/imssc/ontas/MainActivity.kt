//ARRIBA EL BOQUITA PAPA
//Comentario para el push
package mx.itesm.imssc.ontas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val RC_SIGN_IN: Int = 200
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
    }

    fun botonInicio(v: View){
        configurarBotonInicioSesion()
    }

    private fun configurarBotonInicioSesion(){

        binding.btnInicioSesion.setOnClickListener{
            val intInicioSesion = Intent(baseContext, MenuPrincipal::class.java)
            startActivity(intInicioSesion)
        }
    }

    fun signIn(v: View){
        autenticar()
    }

    private fun autenticar(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN
        )
    }

    override fun onStart() {
        super.onStart()
        val usuario = mAuth.currentUser
        if(usuario != null){

            println("Bienvenido: ${usuario?.displayName}")
            //Lanzar directamente segunda pantalla
        }
        else{
            println("Hacer login...")
        }
    }

}