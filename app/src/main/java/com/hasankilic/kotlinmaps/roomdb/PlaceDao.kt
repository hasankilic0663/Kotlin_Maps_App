package com.hasankilic.kotlinmaps.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hasankilic.kotlinmaps.model.Place
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao //veriye erişim objesi crud sistemleri yapmak için

interface PlaceDao {

    @Query("SELECT * FROM Place")
    fun getAll(): Flowable<List<Place>>

    @Insert
    fun insert(place: Place):Completable

    @Delete
    fun delete(place: Place):Completable

}