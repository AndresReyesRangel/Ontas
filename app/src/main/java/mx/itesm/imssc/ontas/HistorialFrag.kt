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
    private lateinit var arrHistorial: MutableList<UsuarioRecibe>
    private lateinit var arrHistorialTokensGenerados: MutableList<String>
    private lateinit var arrHistorialTokensAgregados: MutableList<String>
    private  lateinit var adaptador: AdaptadorTarjetas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //conexión con firebase
        baseDatos= FirebaseDatabase.getInstance()

        //separamos los tokens para saber en cual buscar
        arrHistorialTokensGenerados= mutableListOf()
        arrHistorialTokensAgregados= mutableListOf()
        arrHistorial= mutableListOf()

    }

    private fun configurarRV() {
        val layout= LinearLayoutManager(context)
        rvClientes.layoutManager=layout

        adaptador= AdaptadorTarjetas(arrHistorial)
        rvClientes.adapter= adaptador
    }


    override fun onStart() {
        super.onStart()
        configurarRV()
        leerHistorialTokens()
        //println("onStart")
    }

    private fun leerHistorialTokens() {
        //Historial de tokens Generados
        val userIUD=FirebaseAuth.getInstance().currentUser?.uid
        val referenciaGenerados= baseDatos.getReference("$userIUD/TokensGenerados/")
        referenciaGenerados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("Recibe datos"+ snapshot.toString())
                arrHistorialTokensGenerados.clear()
                arrHistorial.clear()
                for(generados in snapshot.children){
                    arrHistorialTokensGenerados.add(generados.key.toString())
                }
                //println(arrHistorialTokensGenerados)
                checarClientes()
            }

            override fun onCancelled(error: DatabaseError) {
                    println("error")
            }

        })


        
    }
    private  fun descargarTokensRecibidos(){
        //Historial de tokens agregados
        val userIUD=FirebaseAuth.getInstance().currentUser?.uid
        val referenciaAgregados=baseDatos.getReference("$userIUD/TokensAgregados/")
        referenciaAgregados.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrHistorialTokensAgregados.clear()
                for(agregados in snapshot.children){
                    arrHistorialTokensAgregados.add(agregados.key.toString())
                }
                println(arrHistorialTokensAgregados)
                checarVendedores()

            }

            override fun onCancelled(error: DatabaseError) {
                println("Error")
            }
        })
    }

    //Checa que los vendedores ya hayan marcado la venta desactivada
    private fun checarVendedores() {
        for(token in arrHistorialTokensAgregados){
            val tokenint = token.toInt()
            val referenciaToken=baseDatos.getReference("Token/$tokenint/UsuarioRecibe")
            referenciaToken.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val activo = snapshot.child("activo").value.toString()
                        val activoBool = activo.toBoolean()
                        if (!activoBool) {
                            agregarVendedor(tokenint)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
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
                arrHistorial.add(UsuarioRecibe(nombreVendedor,false,imagenVendedor,descripcionObjeto))

                activity?.runOnUiThread {
                    adaptador.arrDatoes=arrHistorial
                    adaptador.notifyDataSetChanged()
                    //println("actualizo adaptador")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error en agregarVendedor")
            }

        })
    }

    //Checa si los clientes ya no estan activos
    private fun checarClientes() {
        for(token in arrHistorialTokensGenerados){
            val tokenint = token.toInt()
            val referenciaToken=baseDatos.getReference("Token/$tokenint/UsuarioRecibe")
            referenciaToken.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //println("checar cliente"+snapshot)
                    if (snapshot.hasChildren()) {
                        val activo = snapshot.child("activo").value.toString()
                        val activoBool = activo.toBoolean()

                        if (!activoBool) {
                            val nombreCliente = snapshot.child("nombreCliente").value.toString()
                            val descripcionObjeto =
                                snapshot.child("descripcionObjeto").value.toString()
                            val imagenCliente = snapshot.child("imagen").value.toString()

                            arrHistorial.add(
                                UsuarioRecibe(
                                    nombreCliente,
                                    activoBool,
                                    imagenCliente,
                                    descripcionObjeto
                                )
                            )
                            //println("Historial despues de checar clientes"+arrHistorial)
                            activity?.runOnUiThread {
                                adaptador.arrDatoes=arrHistorial
                                adaptador.notifyDataSetChanged()
                                //println("actualizo adaptador")
                            }
                            descargarTokensRecibidos()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("no funciono")
                }

            })
        }
        //actualizar adaptador



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

}