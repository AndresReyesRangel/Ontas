package mx.itesm.imssc.ontas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_principal.*

class MenuPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        configurarMenu()
    }

    private fun configurarMenu() {
        menuNavegacion.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navCuenta ->{
                    println("cuenta")
                }

                R.id.navClientes->{
                    println("clientes")
                }

                R.id.navNuevo->{
                    println("nuevo")
                }

                R.id.navHistorial->{
                    println("historial")
                }

                R.id.navOpciones->{
                    println("opciones")
                }
            }
            true
        }
    }
}