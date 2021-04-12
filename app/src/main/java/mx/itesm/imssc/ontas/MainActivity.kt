//ARRIBA EL BOQUITA PAPA

package mx.itesm.imssc.ontas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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