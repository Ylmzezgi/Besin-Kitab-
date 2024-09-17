package com.ezgiyilmaz.besinprojesi.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ezgiyilmaz.besinprojesi.R

fun ImageView.gorselIndir(url:String,placeholder: CircularProgressDrawable){
    val options=RequestOptions().placeholder(placeholder).error(placeholder) //
    Glide.with(this.context)
        .setDefaultRequestOptions(options) // görsel gelene kadar ne gösterileceği
        .load(url) // hangi url gelecek
        .into(this) // hangi imageViewin içine yükleneceği bilgisi verilir

}

fun placeHolderYap(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}