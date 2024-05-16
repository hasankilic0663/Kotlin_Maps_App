package com.hasankilic.kotlinmaps.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hasankilic.kotlinmaps.R
import com.hasankilic.kotlinmaps.adapter.PlaceAdapter
import com.hasankilic.kotlinmaps.databinding.ActivityMainBinding
import com.hasankilic.kotlinmaps.model.Place
import com.hasankilic.kotlinmaps.roomdb.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val compositeDisposable=CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //database olusturma
        val db = Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Place").build()
        val placeDao=db.placeDao()

        compositeDisposable.add(
            placeDao.getAll()//Liste  vericek
                .subscribeOn(Schedulers.io()) // nerede subscribe olucam
                .observeOn(AndroidSchedulers.mainThread()) // nerede gozlemlıcem
                .subscribe(this::handleResponse)


        )


    }
    private fun handleResponse(placeList :List<Place> ) {
        binding.recycleview.layoutManager=LinearLayoutManager(this)
        val adapter= PlaceAdapter(placeList)
        binding.recycleview.adapter=adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // olusturdugun menuyu baglıyon
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.place_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // o menuden secılen bısey secılırse ne olur
        if (item.itemId== R.id.add_place){
            val intent=Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}