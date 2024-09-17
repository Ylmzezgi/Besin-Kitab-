package com.ezgiyilmaz.besinprojesi.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ezgiyilmaz.besinprojesi.model.BesinModel

@Dao
interface BesinDAO {

    @Query("SELECT * FROM besinmodel")
    fun getAllBesin(): List<BesinModel>


    @Query("DELETE FROM besinmodel") // alınan tüm besinleri silecektir
    suspend fun deleteAllBesin()

    @Query("SELECT * FROM besinmodel WHERE uuid= :besinId") // tek bir besin silmek isterse
    suspend fun getBesin(besinId:Int) : BesinModel // tek bir besin dönecek

    @Insert
    suspend fun insertAll(vararg besin:BesinModel) : List<Long>  // tek tek verileri eklemek yerine tek seferde birden fazla besin girebilmek için vararg kullanılır
    //eklediği besinlerin idsini long olarak geri veriyor

}