package com.ezgiyilmaz.besinprojesi.service

import com.ezgiyilmaz.besinprojesi.model.BesinModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {
    val retrofit= Retrofit.Builder()
        .baseUrl("base_url")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BesinAPI::class.java)

    suspend fun getData() :List<BesinModel>{ //verileri alması için
        return retrofit.getBesin() // Besinleri döndürsün
    }
}