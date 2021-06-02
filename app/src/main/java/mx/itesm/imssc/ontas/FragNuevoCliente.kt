package mx.itesm.imssc.ontas

import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_frag_nuevo_cliente.*

//Emiliano Javier Gomez Jimenez
class FragNuevoCliente : Fragment(), ClickListener {

    private  lateinit var  baseDatos: FirebaseDatabase
    private  lateinit var arrCliente: MutableList<UsuarioRecibe>
    private lateinit var arrTokensAgregados: MutableList<String>
    private lateinit var arrTokensGenerados: MutableList<String>
    private lateinit var arrHistorial: MutableList<UsuarioRecibe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Conexion con Firebase
        baseDatos = FirebaseDatabase.getInstance()
        arrCliente= mutableListOf()

    }

    private fun configurarRV() {
        val layoutManager = LinearLayoutManager(context)
        val arrClientesAct = arrCliente.toTypedArray()
        val adaptador = AdaptadorClientesActivos(arrClientesAct)
        rvClientesActivos.layoutManager = layoutManager
        rvClientesActivos.adapter = adaptador
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_nuevo_cliente, container, false)
    }

    override fun onStart() {
        super.onStart()
        configurarRV()
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
                arrCliente.clear()
                for(generados in snapshot.children){
                    arrTokensGenerados.add(generados.key.toString())
                }
                println(arrTokensGenerados)
                checarClientes()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error en obtener clientes", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, "Error en obtener clientes", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun checarVendedores(){
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
                    Toast.makeText(context, "Error en obtener clientes", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    fun agregarVendedor(tokenInt: Int){
        val nuevaReferencia=baseDatos.getReference("Token/$tokenInt/UsuarioGenerador")
        nuevaReferencia.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val nombreVendedor=snapshot.child("nombreCliente").value.toString()
                val descripcionObjeto=snapshot.child("descripcionObjeto").value.toString()
                val imagenVendedor = snapshot.child("imagen").value.toString()
                arrCliente.add(UsuarioRecibe(nombreVendedor,true,imagenVendedor,descripcionObjeto))

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error en obtener clientes", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun checarClientes(){
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

                            arrCliente.add(
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
                    Toast.makeText(context, "Error en obtener clientes", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    override fun clicked(posicion: Int) {
        val intClienteAct = Intent(MenuPrincipal(), ClienteActivo::class.java)
        intClienteAct.putExtra("CLIENTE", posicion)
        startActivity(intClienteAct)
    }


}