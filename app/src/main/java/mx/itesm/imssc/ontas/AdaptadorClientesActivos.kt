package mx.itesm.imssc.ontas

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import kotlinx.android.synthetic.main.tarjetas_clientes_activos.view.*

//Autor: Emiliano Gómez
class AdaptadorClientesActivos (var arrClientesActivos: MutableList<UsuarioRecibe>):
    RecyclerView.Adapter<AdaptadorClientesActivos.vistaRenglonAct>() {

    var listener: ClickListener? = null

        class vistaRenglonAct(val vistaRenglonCliente: View): RecyclerView.ViewHolder(vistaRenglonCliente){
            fun set(cliente: UsuarioRecibe){
                vistaRenglonCliente.tvNomClienteActivo.text = cliente.nombreCliente
                vistaRenglonCliente.tvProducto.text = cliente.descripcionObjeto

                AndroidNetworking.get(cliente.imagen)
                    .build()
                    .getAsBitmap(object : BitmapRequestListener{
                        override fun onResponse(response: Bitmap?) {
                            vistaRenglonCliente.imgPerfilActivo.setImageBitmap(response)
                        }

                        override fun onError(anError: ANError?) {
                            println("Error")
                        }
                    })

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vistaRenglonAct {
        //Crea un renglon
        val vista = LayoutInflater.from(parent.context)
                .inflate(R.layout.tarjetas_clientes_activos, parent, false)
        return vistaRenglonAct(vista)
    }

    override fun onBindViewHolder(holder: vistaRenglonAct, position: Int) {
        //Asigna valroes a los componentes de la vista
        val cliente = arrClientesActivos[position]
        holder.set(cliente)
        holder.vistaRenglonCliente.setOnClickListener {
            listener?.clicked(position)
        }
    }

    override fun getItemCount(): Int {
        //regresa el numero de renglones
        return arrClientesActivos.size
    }
}