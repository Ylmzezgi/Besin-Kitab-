package com.ezgiyilmaz.besinprojesi.service

import com.ezgiyilmaz.besinprojesi.model.BesinModel
import retrofit2.http.GET

interface BesinAPI {


    @GET("base_url")
    suspend fun getBesin() : List<BesinModel> // hangi veriyi döndüreceği yazılır
}