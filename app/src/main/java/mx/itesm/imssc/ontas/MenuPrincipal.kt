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

    //Esta funcion la hizo Javier Martínez
    //Es la configuración del nav menú
    private fun configurarMenu() {
        menuNavegacion.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){

                R.id.navCuenta ->{

                    val fragMiCuenta = MiCuentaFrag()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedorFragmentos, fragMiCuenta).commit()
                }

                R.id.navClientes->{

                }

                R.id.navNuevo->{

                    val fragPantallaPrincipal = MapaPantallaPrincipal()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedorFragmentos, fragPantallaPrincipal).commit()
                }

                R.id.navHistorial->{

                    val fragHistorial = HistorialFrag()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedorFragmentos, fragHistorial).commit()
                }

                R.id.navOpciones->{

                    val fragOpciones = Opciones()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedorFragmentos, fragOpciones).commit()
                }
            }
            true
        }
    }
}