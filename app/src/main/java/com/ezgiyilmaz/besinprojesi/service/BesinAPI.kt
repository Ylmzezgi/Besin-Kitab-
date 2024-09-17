package com.ezgiyilmaz.besinprojesi.service

import com.ezgiyilmaz.besinprojesi.model.BesinModel
import retrofit2.http.GET

interface BesinAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getBesin() : List<BesinModel> // hangi veriyi döndüreceği yazılır
}