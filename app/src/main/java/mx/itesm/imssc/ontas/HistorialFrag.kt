package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_historial.*

//Javier Martínez Hernández A01375496
class HistorialFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun configurarRV() {
        val layout= LinearLayoutManager(context)
        rvClientes.layoutManager=layout

        val arrClientes=crearArrClientes()
        val adaptador= AdaptadorTarjetas(arrClientes)
        rvClientes.adapter= adaptador
    }

    //Cambiar cuando se saque la info por firebase
    private fun crearArrClientes(): Array<UsuarioGenerador> {
        return arrayOf(
                UsuarioGenerador("Andres Morales","Audifonos Logitech"),
                UsuarioGenerador("Cesar Rivera","Xbox series X"),
                UsuarioGenerador("Franciso Bolillo","Pc Master Race")
        )
    }

    override fun onStart() {
        super.onStart()
        configurarRV()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

}