package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_historial.*

//Javier Martínez Hernández A01375496
class HistorialFrag : Fragment() {

    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var arrHistorialClientes: Array<UsuarioRecibe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //conexión con firebase
        baseDatos= FirebaseDatabase.getInstance()

    }

    private fun configurarRV() {
        val layout= LinearLayoutManager(context)
        rvClientes.layoutManager=layout

        arrHistorialClientes=crearArrClientes()
        val adaptador= AdaptadorTarjetas(arrHistorialClientes)
        rvClientes.adapter= adaptador
    }

    //Cambiar cuando se saque la info por firebase
    private fun crearArrClientes(): Array<UsuarioRecibe> {

        /*return arrayOf(
                UsuarioGenerador("Andres Morales","Audifonos Logitech"),
                UsuarioGenerador("Cesar Rivera","Xbox series X"),
                UsuarioGenerador("Franciso Bolillo","Pc Master Race")
        )*/

    }

    override fun onStart() {
        super.onStart()
        configurarRV()
        leerDatos()
    }

    private fun leerDatos() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

}