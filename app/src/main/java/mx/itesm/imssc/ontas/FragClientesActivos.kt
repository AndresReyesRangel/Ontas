package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_agregar_cambio.*
import kotlinx.android.synthetic.main.fragment_frag_clientes_activos.*


//Autor Emiliano Gómez
class FragClientesActivos : Fragment(), ClickListener {

    private  lateinit var  baseDatos: FirebaseDatabase
    private  lateinit var arrCliente: MutableList<UsuarioRecibe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Conexion con Firebase
        baseDatos = FirebaseDatabase.getInstance()
        arrCliente= mutableListOf()
        configurarRV()
    }

    fun configurarRV(){
        val layoutManager = LinearLayoutManager(context)
        val arrClientesAct = arrCliente.toTypedArray()
        val adaptador = AdaptadorClientesActivos(arrClientesAct)
        rvClientesActivos.layoutManager = layoutManager
        rvClientesActivos.adapter = adaptador
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_clientes_activos, container, false)
    }

    fun leerDatos(){
        val uidUsuarioRecibe = FirebaseAuth.getInstance().currentUser.uid
        val referencia = baseDatos.getReference("/$uidUsuarioRecibe/TokensAgregados/")

        referencia.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrCliente.clear()
                for(clientes in snapshot.children){
                    val cliente = clientes.getValue(UsuarioRecibe::class.java)
                    if (cliente != null) {
                        arrCliente.add(cliente)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: al cargar los clientes", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        leerDatos()
    }

    override fun clicked(posicion: Int) {

    }
}