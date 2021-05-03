package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_frag_clientes_activos.*


//Autor Emiliano Gómez
class FragClientesActivos : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun configurarRV(){
        val layout = LinearLayoutManager(context)
        rvClientesActivos.layoutManager = layout

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_clientes_activos, container, false)
    }

    private fun crearArrClientes(): Array<UsuarioRecibe>{
        return arrayOf()
    }

    override fun onStart() {
        super.onStart()
        configurarRV()
    }


}