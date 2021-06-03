package mx.itesm.imssc.ontas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cliente_activo.*

class ClienteActivo : AppCompatActivity() {

    private lateinit var  baseDatos: FirebaseDatabase
    private lateinit var listaClientes: MutableList<UsuarioRecibe>
    private lateinit var arrTokensAgregados: MutableList<String>
    private lateinit var arrTokensGenerados: MutableList<String>

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
        leerTokensGenerados()
    }

    fun leerTokensGenerados(){
        //tokens Generados
        val userIUD=FirebaseAuth.getInstance().currentUser.uid
        val referenciaGenerados= baseDatos.getReference("$userIUD/TokensGenerados/")
        referenciaGenerados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Recibe datos"+ snapshot.toString())
                arrTokensGenerados.clear()
                listaClientes.clear()
                for(generados in snapshot.children){
                    arrTokensGenerados.add(generados.key.toString())
                }
                println(arrTokensGenerados)
                checarClientes()
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al buscar datos")
            }
        })
    }

    fun leerTokensAgregados(){
        //Tokens agregados
        val userIUD=FirebaseAuth.getInstance().currentUser.uid
        val referenciaAgregados=baseDatos.getReference("$userIUD/TokensAgregados/")
        referenciaAgregados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrTokensAgregados.clear()
                for(agregados in snapshot.children){
                    arrTokensAgregados.add(agregados.key.toString())
                }
                println(arrTokensAgregados)
                checarVendedores()

            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al subir datos")
            }
        })
    }

    private fun checarVendedores() {
        for(token in arrTokensAgregados){
            val tokenint = token.toInt()
            val referenciaToken=baseDatos.getReference("Token/$tokenint/UsuarioRecibe")
            referenciaToken.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val activo = snapshot.child("activo").value.toString()
                        val activoBool = activo.toBoolean()
                        if (activoBool) {
                            agregarVendedor(tokenint)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error al subir datos")
                }

            })
        }
    }

    private fun agregarVendedor(tokenint: Int) {
        val nuevaReferencia=baseDatos.getReference("Token/$tokenint/UsuarioGenerador")
        nuevaReferencia.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val nombreVendedor=snapshot.child("nombreCliente").value.toString()
                val descripcionObjeto=snapshot.child("descripcionObjeto").value.toString()
                val imagenVendedor = snapshot.child("imagen").value.toString()
                listaClientes.add(UsuarioRecibe(nombreVendedor,true,imagenVendedor,descripcionObjeto))

            }

            override fun onCancelled(error: DatabaseError) {
                println("Error")
            }

        })
    }

    private fun checarClientes(){
        for(token in arrTokensGenerados){
            val tokenint = token.toInt()
            val referenciaToken=baseDatos.getReference("Token/$tokenint/UsuarioRecibe")
            referenciaToken.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    println("checar cliente"+snapshot)
                    if (snapshot.hasChildren()) {
                        val activo = snapshot.child("activo").value.toString()
                        val activoBool = activo.toBoolean()

                        if (activoBool) {
                            val nombreCliente = snapshot.child("nombreCliente").value.toString()
                            val descripcionObjeto =
                                snapshot.child("descripcionObjeto").value.toString()
                            val imagenCliente = snapshot.child("imagen").value.toString()

                            listaClientes.add(
                                UsuarioRecibe(
                                    nombreCliente,
                                    activoBool,
                                    imagenCliente,
                                    descripcionObjeto
                                )
                            )
                            leerTokensAgregados()
                        }

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    println("Error")
                }

            })
        }
    }

    fun desactivarCliente(v: View){
        val alerta = AlertDialog.Builder(this)
            .setTitle("Desactivar Cliente?")
            .setMessage("Una vez hecho no podrá volverse a reactivar")
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
                val cliente = intent.getIntExtra("CLIENTE",-1)
                for(token in arrTokensGenerados){
                    val tokenint = token.toInt()
                    val ref = baseDatos.getReference("Token/$tokenint/UsuarioRecibe")
                    if(ref.equals(listaClientes[cliente]))
                        ref.child("activo").setValue("false")
                        listaClientes[cliente].Activo = false
                }
            })
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)

        alerta.show()
    }
}