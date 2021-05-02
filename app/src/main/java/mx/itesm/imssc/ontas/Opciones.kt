package mx.itesm.imssc.ontas

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

class Opciones : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opciones, container, false)
    }

    //Falta conectar a firebase para que haga la jalación :)
/*
    fun hacerSignOut(v: View) {
        AuthUI.getInstance().signOut(this).addOnSuccessListener {
            val logOut = Intent(this, MainActivity::class.java)
            startActivity(logOut)
            Toast.makeText(this.requireContext(), "Se ha cerrado tu sesión", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Ocurrio un error ${it.message}", Toast.LENGTH_SHORT).show()

        }
    }*/

}