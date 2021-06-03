package mx.itesm.imssc.ontas

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.DirectedAcyclicGraph
import androidx.core.app.ActivityCompat
import mx.rmr.make.gps.GPS
import mx.rmr.make.gps.GPSListener
import android.Manifest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_mapa_pantalla_principal.*

class MapaPantallaPrincipal : Fragment(), GPSListener, OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    /*
    private val CODIGO_PERMISO_GPS: Int = 200
    private var gps: GPS? = null
    private lateinit var contexto: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onStart(){
        super.onStart()
        configurarGPS()
    }

    private fun configurarGPS(){

        gps = GPS()
        gps?.gpsListener = this
        gps?.inicializar(contexto )

        if(verificarPermiso()){
            iniciarActualizacionesPosicion()
        }else{
            pedirPermisos()
        }

    }

     */


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        map?.let{
            googleMap = it
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_mapa_pantalla_principal, container, false)
    }

    override fun actualizarPosicion(posicion: Location) {
        TODO("Not yet implemented")
    }

    

    /*
    private fun pedirPermisos(){

        val requiereJustificacion = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        if(requiereJustificacion){
            mostrarDialogo()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS)
        }
    }

    private fun iniciarActualizacionesPosicion(){
        gps?.iniciarActualizaciones()
    }

    private fun mostrarDialogo(){
        val dialogo = AlertDialog.Builder(context)
        dialogo.setMessage("Necesitas GPS para esta app.")
            .setPositiveButton("Aceptar"){
                dialog, which -> ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS)
            }
            .setNeutralButton("Cancelar", null)
        dialogo.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CODIGO_PERMISO_GPS){
            if(grantResults.isEmpty()){
            }else if (grantResults.first() == PackageManager.PERMISSION_GRANTED){
                gps?.iniciarActualizaciones()
            }else{
                val dialogo = AlertDialog.Builder(context)
                dialogo.setMessage("Esta app requiere GPS")
                    .setPositiveButton("Aceptar",DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
                    .setNeutralButton("Cancelar", DialogInterface.OnClickListener{dialog, which ->
                        println("No hay forma de usar gps")
                    })
                    .setCancelable(false)
                dialogo.show()

            }
        }
    }

    private fun verificarPermiso(): Boolean{
        val estadoPermiso = ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESSS_FINE_LOCATION)
        return estadoPermiso == PackageManager.PERMISSION_GRANTED
    }

    override fun actualizarPosicion(posicion: Location) {
        TODO("Not yet implemented")
    }

     */


}