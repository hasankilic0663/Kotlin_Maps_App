package com.hasankilic.kotlinmaps.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Place(
            @ColumnInfo(name = "name")//kolon ismi
            var name: String,
            @ColumnInfo(name = "latitude")
            var latitude :Double,

            @ColumnInfo(name = "longitude")
            var longitude:Double

            ) {

    @PrimaryKey(autoGenerate = true)//otomatik id veriyo
    var id =0
}