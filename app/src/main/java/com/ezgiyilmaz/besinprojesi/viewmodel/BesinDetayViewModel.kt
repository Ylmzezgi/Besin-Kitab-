package com.ezgiyilmaz.besinprojesi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ezgiyilmaz.besinprojesi.model.BesinModel
import com.ezgiyilmaz.besinprojesi.roomdb.BesinDAO
import com.ezgiyilmaz.besinprojesi.roomdb.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application: Application) :AndroidViewModel(application){
    //tek bir besinin detayını göstereceği için tek bir besini tutacak livedata oluşturulur
    val besinLiveData=MutableLiveData<BesinModel>()

    fun roomVerisiniAl(uuid : Int) {
        viewModelScope.launch{
            val dao =BesinDatabase(getApplication()).besinDao()
            val besin=dao.getBesin(uuid)
            besinLiveData.value=besin

        }
    }
}