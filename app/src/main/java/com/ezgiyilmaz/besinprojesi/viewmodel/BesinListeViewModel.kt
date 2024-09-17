package com.ezgiyilmaz.besinprojesi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezgiyilmaz.besinprojesi.model.BesinModel
import com.ezgiyilmaz.besinprojesi.roomdb.BesinDatabase
import com.ezgiyilmaz.besinprojesi.service.BesinAPIServis
import com.ezgiyilmaz.besinprojesi.util.OzelSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListeViewModel(application: Application): AndroidViewModel(application) {

//hata mesajları verilerin yüklenip yüklenmediğini UI ı etkliyen ögeleri liveData da kullanıcam

    val besinler=MutableLiveData<List<BesinModel>>()
    val besinHataMesajı=MutableLiveData<Boolean>()
    val besinYükleniyor= MutableLiveData <Boolean>()


    private val besinApiServis=BesinAPIServis()
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())

    private val guncellemeZamnai=0.1*60*1000*1000*1000L

    fun refreshData(){
        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()

        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamnai){
            verileriRoomdanAl()
        }else{
            verileriInternettenAl()
        }
    }

    fun refreshDataFromInternet(){
    verileriInternettenAl()

    }

    private fun verileriRoomdanAl(){
        besinYükleniyor.value=true
        viewModelScope.launch (Dispatchers.IO) {
            val besinleriRoomdanAlanListe = BesinDatabase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main){ // kullanıcıya göstereceğimiz için kullanmamız gerekiyor Main
                besinleriGoster(besinleriRoomdanAlanListe)
                Toast.makeText(getApplication(),"Besinleri Roomdan Aldık",Toast.LENGTH_LONG).show()

            }
        }
    }


    private fun verileriInternettenAl(){ // verileri internetten çekip rooma kaydettik
        besinYükleniyor.value=true

        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi=besinApiServis.getData() // getData suspend bir fonksiyon olduğu için coroutine içinde kullanmamız gerekiyor. Burada besinleri liste halinde çektik
            withContext(Dispatchers.Main){
                besinYükleniyor.value=false
                //rooma kayıt
                roomaKaydet(besinListesi) // listeyi rooma kaydettik
                Toast.makeText(getApplication(),"Besinleri İnternetten Aldık",Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun besinleriGoster(besinListesi: List<BesinModel>){
        besinler.value=besinListesi
        besinHataMesajı.value=false
        besinYükleniyor.value=false
    }


    private fun roomaKaydet(besinListesi: List<BesinModel>){
        //Dao da yaptığımız bütün fonksiyonları suspend yaptığımız için o fonksiyoları kullanırken scope kullanmamız gerekiyor
        viewModelScope.launch {
            val dao=BesinDatabase(getApplication()).besinDao() //
            dao.deleteAllBesin()// internetten veri gelirse içersindekileri temizle
            val uuidListesi=dao.insertAll(*besinListesi.toTypedArray()) // her bir besine id atadık
            var i=0
            while (i<besinListesi.size){
                besinListesi[i].uuid=uuidListesi[i].toInt()
                i=i+1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }

}