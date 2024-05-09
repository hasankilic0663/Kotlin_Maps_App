package com.hasankilic.kotlinmaps

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hasankilic.kotlinmaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    //konum yoneticisi ve konum dinleyicisi yazıcam
    private lateinit var locationManager:LocationManager
    private lateinit var locationListener: LocationListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        locationManager=this.getSystemService(LOCATION_SERVICE) as LocationManager // location servsie kesin var diycez servisi oyuzdenas loıcationservice yaazdk

        /*locationListener=LocationListener{location ->
            location.                                           bu sekılde kullannılır diğer kullanımıda altta
        }*/
        locationListener=object :LocationListener{
            override fun onLocationChanged(location: Location) { // her konum değiştiginde yeni locationu verıyor
                TODO("Not yet implemented")
            }

        }
                                                                    //kac sanıyede bir ve kac metre uzaklıktacagırcagımız rakamları
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)



        //latitude , longitude

        //lat-> 48.85391 , lon->2.2913515
/*

        val eiffel = LatLng(48.85391,2.2913515)
        mMap.addMarker(MarkerOptions().position(eiffel).title("Eiffel Tower"))//ikon
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15f))//zoomlama


 */
    }
}