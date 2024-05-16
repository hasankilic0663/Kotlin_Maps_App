package com.hasankilic.kotlinmaps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasankilic.kotlinmaps.databinding.RecycleRowBinding
import com.hasankilic.kotlinmaps.model.Place
import com.hasankilic.kotlinmaps.view.MapsActivity

class PlaceAdapter(val placeList: List<Place>): RecyclerView.Adapter<PlaceAdapter.PlaceHolder>() {
    class PlaceHolder(val recycleRowBinding: RecycleRowBinding) : RecyclerView.ViewHolder(recycleRowBinding.root){ // burası bızım butun gorunumzleerımızı baglayan sınıftı

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val recycleRowBinding = RecycleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PlaceHolder(recycleRowBinding)

    }


    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {

        holder.recycleRowBinding.recycleViewTextView.text=placeList.get(position).name
        holder.itemView.setOnClickListener {
            val intent =Intent(holder.itemView.context,MapsActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }


}