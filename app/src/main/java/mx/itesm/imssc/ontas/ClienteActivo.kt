package mx.itesm.imssc.ontas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cliente_activo.*

class ClienteActivo : AppCompatActivity() {

    private lateinit var  baseDatos: FirebaseDatabase
    private lateinit var listaClientes: MutableList<UsuarioRecibe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_activo)
        baseDatos = FirebaseDatabase.getInstance()
        listaClientes= mutableListOf()
        val cliente = intent.getIntExtra("CLIENTE",-1)

        tvNomCliente.setText(listaClientes[cliente].nombreCliente)
        tvProductoCAAct.setText(listaClientes[cliente].descripcionObjeto)
    }

    override fun onStart() {
        super.onStart()
        leerDatos()
    }

    fun leerDatos(){
        val uidUsuarioRecibe = FirebaseAuth.getInstance().currentUser.uid
        val referencia = baseDatos.getReference("/$uidUsuarioRecibe/TokensAgregados/")

        referencia.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaClientes.clear()
                for(clientes in snapshot.children){
                    val cliente = clientes.getValue(UsuarioRecibe::class.java)
                    if (cliente != null) {
                        listaClientes.add(cliente)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(context, "Error al obtener información del cliente", Toast.LENGTH_SHORT).show()
            }
        })
    }
}