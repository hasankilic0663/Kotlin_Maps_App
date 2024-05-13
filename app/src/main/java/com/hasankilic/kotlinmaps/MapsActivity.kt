package com.hasankilic.kotlinmaps

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.hasankilic.kotlinmaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,GoogleMap.OnMapLongClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    //konum yoneticisi ve konum dinleyicisi yazıcam
    private lateinit var locationManager:LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var takipetmeBoolean:Boolean?=null
    private var selectedLatitude :Double? =null
    private var selectedLongitude:Double?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()

        sharedPreferences=this.getSharedPreferences("com.hasankilic.kotlinmaps", MODE_PRIVATE)
        takipetmeBoolean=false
        selectedLatitude=0.0
        selectedLongitude=0.0
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

        locationManager=this.getSystemService(LOCATION_SERVICE) as LocationManager // location servsie kesin var diycez servisi oyuzdenas loıcationservice yaazdk

        /*locationListener=LocationListener{location ->
            location.                                           bu sekılde kullannılır diğer kullanımıda altta
        }*/
        locationListener=object :LocationListener{
            override fun onLocationChanged(location: Location) { // her konum değiştiginde yeni locationu verıyor

                takipetmeBoolean= sharedPreferences.getBoolean("takipetme",false)
                if (takipetmeBoolean==false){
                    val userLocation=LatLng(location.latitude,location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                    sharedPreferences.edit().putBoolean("takipetme",true).apply()
                }

            }

        }
                                                                    //kac sanıyede bir ve kac metre uzaklıktacagırcagımız rakamları


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(binding.root,"Permisiion needed for location ",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                    //request permission
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                }.show()
            }else{
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }else{
            //permission granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation!=null){
                val lastUserLocation=LatLng(lastLocation.latitude,lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))

            }
            mMap.isMyLocationEnabled=true
        }
    //latitude , longitude

        //lat-> 48.85391 , lon->2.2913515
/*

        val eiffel = LatLng(48.85391,2.2913515)
        mMap.addMarker(MarkerOptions().position(eiffel).title("Eiffel Tower"))//ikon
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15f))//zoomlama


 */
    }
    private fun registerLauncher(){
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
            if (result){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    //permission Granted
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)

                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastLocation!=null){
                        val lastUserLocation=LatLng(lastLocation.latitude,lastLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))
                    }
                }
            }else{
                Toast.makeText(this@MapsActivity,"Permission needed!" ,Toast.LENGTH_LONG).show()
            }

        }

    }



    override fun onMapLongClick(p0: LatLng) { // uzun tıklayınca ne olacak metodu
        mMap.clear() // her seferinde bi onceki kırmızı imleçi siliyor
       mMap.addMarker(MarkerOptions().position(p0))
        selectedLatitude=p0.latitude//enlem
        selectedLongitude=p0.longitude // boylam . burada ıkısınıde alıp verıtabanına kaydedıcez sectıgımız yeri yani
    }

    fun save(view:View){

    }
    fun delete(view:View){

    }

}