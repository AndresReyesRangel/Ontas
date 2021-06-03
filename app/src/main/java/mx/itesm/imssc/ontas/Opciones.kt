package mx.itesm.imssc.ontas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_opciones.*
import mx.itesm.imssc.ontas.databinding.ActivityMainBinding

class Opciones : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var contexto: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opciones, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    //Falta conectar a firebase para que haga la jalación :)

    fun hacerSignOut(v: View) {
        mAuth.currentUser?.delete()
            /*
            .addOnSuccessListener {
            val logOut = Intent(contexto, MainActivity::class.java)
            startActivity(logOut)
            Toast.makeText(contexto, "Se ha cerrado tu sesión", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(contexto, "Ocurrio un error ${it.message}", Toast.LENGTH_SHORT).show()
        }*/
    }

}