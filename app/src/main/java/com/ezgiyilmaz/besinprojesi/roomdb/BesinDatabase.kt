package com.ezgiyilmaz.besinprojesi.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ezgiyilmaz.besinprojesi.model.BesinModel

@Database(entities = [BesinModel::class], version = 1)
abstract class BesinDatabase : RoomDatabase(){
    abstract fun besinDao() : BesinDAO

    companion object {
        @Volatile
        private var instance :BesinDatabase?=null
        private val lock=Any()
        operator fun invoke(context:Context) = instance?: synchronized(lock){
            instance ?:databaseOlustur(context).also{
                instance=it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDatabase::class.java,
            "BesinDatabase"
        ).build()
    }
}