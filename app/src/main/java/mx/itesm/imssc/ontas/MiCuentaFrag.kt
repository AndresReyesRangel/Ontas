//Autor: Ricardo David Zambrano Figueroa A01379700
//ARRIBA EL BOQUITA PAPA

package mx.itesm.imssc.ontas

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_mi_cuenta.*
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding


class MiCuentaFrag : Fragment() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun cambiarNombre(){
        mAuth = FirebaseAuth.getInstance()
        val usuario = mAuth.currentUser
        val nombre = usuario?.displayName
        tvNombreUsuario.text = "$nombre"
    }

    fun cambiarFotoPerfil(){
        mAuth = FirebaseAuth.getInstance()
        val usuario = mAuth.currentUser
        val dirImagen = usuario?.photoUrl.toString()
        AndroidNetworking.get(dirImagen).build().getAsBitmap(object : BitmapRequestListener {
            override fun onResponse(response: Bitmap?) {
                imgPerfil.setImageBitmap(response)
            }

            override fun onError(anError: ANError?) {
                imgPerfil.setImageBitmap(null)
            }

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_cuenta, container, false)
    }

    override fun onStart() {
        super.onStart()
        cambiarNombre()
        cambiarFotoPerfil()
    }

}