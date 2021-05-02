package mx.itesm.imssc.ontas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_frag_nuevo_cliente.*

//Emiliano Javier Gomez Jimenez
class FragNuevoCliente : Fragment() {

    lateinit var arrTokens: MutableList<String>
    lateinit var  arrUsuarioGenerador: MutableList<UsuarioGenerador>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrTokens = mutableListOf()
        arrUsuarioGenerador = mutableListOf()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_nuevo_cliente, container, false)
    }

    override fun onStart() {
        super.onStart()

    }
    //Usar este cuando tengamos conexión a Firebase
    private fun leerDatos() {
        val baseDatos = FirebaseDatabase.getInstance()
        val referencia = baseDatos.getReference("");

        referencia.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun buscar(v: View){
        agregarCliente()
    }

    fun cancelar(v: View){
        quitarTexto()
    }

    private fun agregarCliente(){
        arrUsuarioGenerador.add(UsuarioGenerador(inputToken.text.toString(),inputProducto.text.toString()))
    }

    private fun quitarTexto(){
        inputToken.setText("")
        inputProducto.setText("")
    }
}