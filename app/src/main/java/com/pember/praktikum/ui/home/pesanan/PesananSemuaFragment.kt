package com.pember.praktikum.ui.home.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pember.praktikum.utils.MappingHelper
import com.pember.praktikum.databinding.FragmentFilterPesananBinding
import com.pember.praktikum.db.TransaksiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PesananSemuaFragment: Fragment() {
    private lateinit var binding: FragmentFilterPesananBinding
    private lateinit var itemPesananAdapter: ItemPesananAdapter


    companion object {
        var ID_USER = "idUser"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterPesananBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNotesAsync()
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
//            binding.progressbar.visibility = View.VISIBLE
            val transaksiHelper = TransaksiHelper.getInstance(requireContext())
            transaksiHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = transaksiHelper.queryAllByUser(ID_USER)
                MappingHelper.mapTransaksiCursorToArrayList(cursor)
            }
//            binding.progressbar.visibility = View.INVISIBLE
            val transaksis = deferredNotes.await()
            if (transaksis.size > 0) {
                itemPesananAdapter = ItemPesananAdapter()
                itemPesananAdapter.listTransaksi = transaksis
                binding.rv.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = itemPesananAdapter
                }
            } else {
                itemPesananAdapter = ItemPesananAdapter()
                itemPesananAdapter.listTransaksi = ArrayList()
                binding.rv.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = itemPesananAdapter
                }
                Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
            }
            transaksiHelper.close()
        }
    }
}