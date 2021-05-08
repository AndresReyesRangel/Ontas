package mx.itesm.imssc.ontas

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import kotlinx.android.synthetic.main.tarjetas.view.*

//Autor Javier Martinez
//Esta clase es para administrar las tarjetas de los clientes
//aaaaaaa
class AdaptadorTarjetas(private val arrDatoes: Array<UsuarioRecibe>):
    RecyclerView.Adapter<AdaptadorTarjetas.VistaRenglon>() {

    class VistaRenglon(val vistaRenglonCliente: View) : RecyclerView.ViewHolder(vistaRenglonCliente){
        fun set(usuarioRecibe: UsuarioRecibe){
            //println("cliente: $usuarioRecibe")
            vistaRenglonCliente.tvNombreCliente.text=usuarioRecibe.nombreCliente
            vistaRenglonCliente.tvDescripcionObjeto.text=usuarioRecibe.descripcionObjeto

            //Imagen para descargar de internet con el url que nos da la base de datos
            AndroidNetworking.get(usuarioRecibe.imagen)
                .build()
                .getAsBitmap(object: BitmapRequestListener{
                    override fun onResponse(response: Bitmap?) {
                        vistaRenglonCliente.imgCliente.setImageBitmap(response)
                    }

                    override fun onError(anError: ANError?) {
                        println("Error")
                    }

                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaRenglon {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.tarjetas, parent, false)
        return VistaRenglon(vista)
    }

    //Pedir datos de un renglon
    override fun onBindViewHolder(holder: VistaRenglon, position: Int) {
        val clienteTarjeta=arrDatoes[position]
        holder.set(clienteTarjeta)
        //println("Creando renglon $position")
    }

    override fun getItemCount(): Int {
        //println("imprimendo el tamaño: ${arrDatoes.size}")
        return arrDatoes.size
    }
}

