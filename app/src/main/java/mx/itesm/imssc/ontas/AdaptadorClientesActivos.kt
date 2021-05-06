package mx.itesm.imssc.ontas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tarjetas_clientes_activos.view.*

//Autor: Emiliano Gómez
class AdaptadorClientesActivos (private val arrClientesActivos: Array<UsuarioRecibe>):
    RecyclerView.Adapter<AdaptadorClientesActivos.vistaRenglonAct>() {

    var listener: ClickListener? = null

        class vistaRenglonAct(val vistaRenglonCliente: View): RecyclerView.ViewHolder(vistaRenglonCliente){
            fun set(cliente: UsuarioRecibe){
                vistaRenglonCliente.tvNomClienteActivo.text = cliente.nombreCliente
                //Pendiente la imagen
                //vistaRenglonCliente.imgPerfilActivo.setImageURI(cliente.imagen)
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