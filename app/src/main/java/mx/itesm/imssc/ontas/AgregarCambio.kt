package mx.itesm.imssc.ontas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_agregar_cambio.*

//Clase hecha por Javier Martínez
class AgregarCambio : AppCompatActivity() {
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var arrTokensRegistrados: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_cambio)

        //conexión con firebase
        baseDatos= FirebaseDatabase.getInstance()
        arrTokensRegistrados= mutableListOf()

    }

    override fun onStart() {
        super.onStart()
        leerDatos()
    }

    //Para Generar el Token de quien publica
    fun generarToken(v: View) {
        generartoken()
    }

    private fun generartoken() {
        //Datos del cliente generador
        val objeto = tiDescripcionObjeto.text.toString()
        val usuarioGenerador = FirebaseAuth.getInstance().currentUser.displayName
        val fotoUsuarioGenerador = FirebaseAuth.getInstance().currentUser.photoUrl.toString()
        val clienteGenerador= UsuarioGenerador(usuarioGenerador,objeto,fotoUsuarioGenerador)

        //Token Generado
        var tokenGenerator=(1000..100000).random()

        //Me aseguro de que los tokens siempre sean distintos
        for(token in arrTokensRegistrados){
            if(token==tokenGenerator){
                tokenGenerator=(1000..100000).random()
            }
        }

        tvTokenGenerado.setText("$tokenGenerator")
        val escritura=baseDatos.getReference("/Token/$tokenGenerator/UsuarioGenerador/")
        escritura.setValue(clienteGenerador)
    }

    //Agregar a quien recibe el token
    fun tokenAgregado(v: View){
        tokenAgregado()
    }
    private fun tokenAgregado(){
        val tokenRecibido=tiToken.text.toString().toInt()
        val usuarioRecibe = FirebaseAuth.getInstance().currentUser.displayName
        val fotoUsuarioRecibe = FirebaseAuth.getInstance().currentUser.photoUrl.toString()
        val usuario=UsuarioRecibe(usuarioRecibe,fotoUsuarioRecibe,true)
        //Bandera para ver si existe el token o no
        var valido=false
        for(token in arrTokensRegistrados){
            if(token==tokenRecibido){
                valido=true
                val escritura=baseDatos.getReference("/Token/$token/UsuarioRecibe/")
                escritura.setValue(usuario)
                break
            }
        }

        if(valido){
            tvInfoVenta.setText("Se generó correctamente el enlace a su vendedor")
        }
        else{
            tvInfoVenta.setText("El token que escribio es incorrecto o no existe")
        }
    }

    //lee los datos de la base de datos y los mete en el arreglo
    private fun leerDatos(){
        val referencia = baseDatos.getReference("/Token/")

        referencia.addValueEventListener(object: ValueEventListener { //Tiempo real
            //asincrono
            override fun onDataChange(snapshot: DataSnapshot) {
                arrTokensRegistrados.clear()
                for (registro in snapshot.children){
                    val tokensRegistrados=registro.key
                    if(tokensRegistrados!=null){
                        arrTokensRegistrados.add(tokensRegistrados.toInt())
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                println("error al descargar los datos")
            }

        })

    }

}