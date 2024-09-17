package com.ezgiyilmaz.besinprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ezgiyilmaz.besinprojesi.databinding.BesinRecyclerRowBinding
import com.ezgiyilmaz.besinprojesi.model.BesinModel
import com.ezgiyilmaz.besinprojesi.util.gorselIndir
import com.ezgiyilmaz.besinprojesi.util.placeHolderYap
import com.ezgiyilmaz.besinprojesi.view.BesinListeFragmentDirections

class BesinRecyclerAdapter(val besinListesi :ArrayList<BesinModel>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {
    class BesinViewHolder(val binding : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding=BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.isim.text=besinListesi[position].besinIsim
        holder.binding.kalori.text=besinListesi[position].besinKalori

        holder.itemView.setOnClickListener {
            val action=BesinListeFragmentDirections.actionBesinListeFragment2ToBesinDetayFragment2(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        besinListesi.get(position).besinGorsel?.let {
            holder.binding.imageView.gorselIndir(it, placeHolderYap(holder.itemView.context))
        }
    }

    fun besinListesiniGuncelle(yenibesinlistesi:List<BesinModel>){
        besinListesi.clear()
        besinListesi.addAll(yenibesinlistesi)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }
}

