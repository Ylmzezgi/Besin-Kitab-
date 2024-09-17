package com.ezgiyilmaz.besinprojesi.util
import android.content.Context
import android.content.SharedPreferences

class OzelSharedPreferences {

    companion object {
        private val TIME = "time"
        private var sharedpreferences: SharedPreferences? = null


        @Volatile
        private var instance: OzelSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferencesOluştur(context).also {
                instance = it
            }
        }

        private fun ozelSharedPreferencesOluştur(context: Context): OzelSharedPreferences {
            sharedpreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }


    }
    fun zamaniKaydet(zaman: Long) {
        sharedpreferences?.edit()?.putLong(TIME, zaman)?.apply()
    }

    fun zamaniAl() = sharedpreferences?.getLong(TIME,0)
}