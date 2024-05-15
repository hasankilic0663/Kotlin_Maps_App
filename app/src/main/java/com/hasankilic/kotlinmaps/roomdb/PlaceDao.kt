package com.hasankilic.kotlinmaps.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hasankilic.kotlinmaps.model.Place


@Dao //veriye erişim objesi crud sistemleri yapmak için

interface PlaceDao {

    @Query("SELECT * FROM Place")
    fun getAll(): List<Place>

    @Insert
    fun insert(place: Place)

    @Delete
    fun delete(place: Place)

}