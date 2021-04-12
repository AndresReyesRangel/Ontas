//ARRIBA EL BOQUITA PAPA
//Comentario para el
package mx.itesm.imssc.ontas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

}