package com.ezgiyilmaz.besinprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezgiyilmaz.besinprojesi.adapter.BesinRecyclerAdapter
import com.ezgiyilmaz.besinprojesi.databinding.FragmentBesinListeBinding
import com.ezgiyilmaz.besinprojesi.service.BesinAPI
import com.ezgiyilmaz.besinprojesi.viewmodel.BesinListeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinListeFragment : Fragment() {
private var _binding:FragmentBesinListeBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel : BesinListeViewModel
    private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding=FragmentBesinListeBinding.inflate(inflater,container,false)
        val view= binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this)[BesinListeViewModel::class.java]
        viewModel.refreshData()

        binding?.BesinreclerViewId?.layoutManager=LinearLayoutManager(requireContext())
        binding?.BesinreclerViewId?.adapter=besinRecyclerAdapter

        binding!!.refreshld?.setOnRefreshListener { // sayfayı yukarıdan aşağıya çektiğimizde sayfanın yenilenmesi için yazılacak kod
            binding!!.besinHataMesaj.visibility=View.GONE
            binding!!.BesinreclerViewId.visibility=View.GONE
            binding!!.besinYKleniyor.visibility=View.VISIBLE
            viewModel!!.refreshDataFromInternet()
            binding!!.refreshld.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel!!.besinler.observe(viewLifecycleOwner) {
            //adapter
            besinRecyclerAdapter.besinListesiniGuncelle(it)
            binding!!.BesinreclerViewId.visibility = View.VISIBLE
        }

        viewModel!!.besinHataMesajı.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.besinHataMesaj.visibility = View.VISIBLE
                binding!!.BesinreclerViewId.visibility = View.GONE
            } else {
                binding!!.besinHataMesaj.visibility = View.GONE
            }
        }
            viewModel.besinYükleniyor.observe(viewLifecycleOwner) {
                if (it) {
                    binding!!.besinHataMesaj.visibility = View.VISIBLE
                    binding!!.BesinreclerViewId.visibility = View.GONE
                    binding!!.besinYKleniyor.visibility = View.VISIBLE
                } else {
                    binding!!.besinYKleniyor.visibility = View.GONE
                }
            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}