//ARRIBA EL BOQUITA PAPA
//Comentario para el push
package mx.itesm.imssc.ontas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.UserWriteRecord
import kotlinx.android.synthetic.main.activity_main.*
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val RC_SIGN_IN: Int = 200
    private lateinit var mAuth: FirebaseAuth

    //Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()


    }

    fun botonInicio(v: View){
        configurarBotonInicioSesion()
    }

    private fun configurarBotonInicioSesion() {

        val intInicioSesion = Intent(baseContext, MenuPrincipal::class.java)
        startActivity(intInicioSesion)
    }


    //Código para crear una nueva cuenta ya sea Facebook, Google, Twitter o Email
    fun signIn(v: View){
        autenticar()
    }

    private fun autenticar(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN
        )
    }

    //Si no estaba firmado, despliega la ventana para hacer sign in
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            when(resultCode){
                RESULT_OK ->{
                    val usuario = FirebaseAuth.getInstance().currentUser
                    println("Bienvenido: ${usuario.displayName}")
                    println("Bienvenido: ${usuario.email}")
                    println("Bienvenido: ${usuario.uid}")
                    val intInicioSesion = Intent(baseContext, MenuPrincipal::class.java)
                    startActivity(intInicioSesion)
                }
                RESULT_CANCELED -> {
                    println("Cancelado (back)")
                }
                else -> {
                    val response = IdpResponse.fromResultIntent(data)
                    println("Error: ${response?.error?.errorCode}")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val usuario = mAuth.currentUser
        if (usuario != null){
            //Ya está firmado
            println("Bienvenido de vuelta")

            //Si ya está firmado entonces mandar a la pantalla principal

            val intInicioSesion = Intent(baseContext, MenuPrincipal::class.java)
            startActivity(intInicioSesion)
        }else{
            println("Hacer Login......")
        }
    }

}