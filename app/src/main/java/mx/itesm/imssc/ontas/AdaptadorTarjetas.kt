package mx.itesm.imssc.ontas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tarjetas.view.*

//Autor Javier Martinez
//Esta clase es para administrar las tarjetas de los clientes
class AdaptadorTarjetas(private val arrDatos: Array<Cliente>):

    RecyclerView.Adapter<AdaptadorTarjetas.VistaRenglon>() {
    class VistaRenglon(val vistaRenglonCliente: View) : RecyclerView.ViewHolder(vistaRenglonCliente){
        fun set(cliente: Cliente){
            vistaRenglonCliente.tvNombreCliente.text=cliente.nombreCliente
            vistaRenglonCliente.tvDescripcionObjeto.text=cliente.descripcionObjeto
            //Imagen pendiente para descargar de internet
            //vistaRenglonCliente.imgCliente.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaRenglon {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.tarjetas, parent, false)
        return VistaRenglon(vista)
    }

    //Pedir datos de un renglon
    override fun onBindViewHolder(holder: VistaRenglon, position: Int) {
        val clienteTarjeta=arrDatos[position]
        holder.set(clienteTarjeta)
    }

    override fun getItemCount(): Int {
        return arrDatos.size
    }
}

