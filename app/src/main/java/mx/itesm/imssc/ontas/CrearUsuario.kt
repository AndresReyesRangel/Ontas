package mx.itesm.imssc.ontas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.imssc.ontas.databinding.ActivityCrearUsuarioBinding
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding

class CrearUsuario : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityCrearUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
    }

    fun log(v: View) {
        logIn()
    }

    private fun logIn() {
        var email = binding.etCorreo.text
        var password = binding.etContraseA.text

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Log.d(TAG, "createUserWithEmail:success")
                    val usuario = FirebaseAuth.getInstance().currentUser
                    println("Bienvenido: ${usuario?.displayName}")
                    println("Bienvenido: ${usuario?.email}")
                    println("Bienvenido: ${usuario?.uid}")
                    val intInicioSesion = Intent(baseContext, MenuPrincipal::class.java)
                    startActivity(intInicioSesion)
                } else {
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
    }
}