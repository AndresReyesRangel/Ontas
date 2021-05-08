package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_historial.*

//Javier Martínez Hernández A01375496
class HistorialFrag : Fragment() {

    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var arrHistorialClientes: Array<UsuarioRecibe>
    private lateinit var arrHistorialTokensGenerados: MutableList<String>
    private lateinit var arrHistorialTokensAgregados: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //conexión con firebase
        baseDatos= FirebaseDatabase.getInstance()

        //separamos los tokens para saber en cual buscar
        arrHistorialTokensGenerados= mutableListOf()
        arrHistorialTokensAgregados= mutableListOf()

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

        return arrayOf(
                UsuarioRecibe("Andres Morales",true),
                UsuarioRecibe("Cesar Rivera",true),
                UsuarioRecibe("Franciso Bolillo",true)
        )

    }

    override fun onStart() {
        super.onStart()
        configurarRV()
        leerHistorialTokens()
    }

    private fun leerHistorialTokens() {
        //Historial de tokens Generados
        val userIUD=FirebaseAuth.getInstance().currentUser.uid
        val referenciaGenerados= baseDatos.getReference("/Tokens/$userIUD/TokensGenerados/")
        referenciaGenerados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(generados in snapshot.children){
                    arrHistorialTokensGenerados.add(generados.key.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                    println("error")
            }

        })

        //Historial de tokens agregados
        val referenciaAgregados=baseDatos.getReference("/Tokens/$userIUD/TokensAgregados/")
        referenciaAgregados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(agregados in snapshot.children){
                    arrHistorialTokensAgregados.add(agregados.key.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error")
            }
        })

        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

}