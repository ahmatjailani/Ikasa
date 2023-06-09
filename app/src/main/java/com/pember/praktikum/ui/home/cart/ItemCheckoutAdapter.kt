package com.pember.praktikum.ui.home.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pember.praktikum.utils.MappingHelper
import com.pember.praktikum.R
import com.pember.praktikum.data.Transaksi
import com.pember.praktikum.databinding.ItemCheckoutBinding
import com.pember.praktikum.db.IkanHelper

class ItemCheckoutAdapter: RecyclerView.Adapter<ItemCheckoutAdapter.ItemCheckoutViewHolder>() {
    private lateinit var ikanHelper: IkanHelper
    var listTransaksi = ArrayList<Transaksi>()
        set(listTransaksi) {
            if (listTransaksi.size > 0) {
                this.listTransaksi.clear()
            }
            this.listTransaksi.addAll(listTransaksi)
        }
    fun addItem(transaksi: Transaksi) {
        this.listTransaksi.add(transaksi)
        notifyItemInserted(this.listTransaksi.size - 1)
    }
    fun updateItem(position: Int, transaksi: Transaksi) {
        this.listTransaksi[position] = transaksi
        notifyItemChanged(position, transaksi)
    }
    fun removeItem(position: Int) {
        this.listTransaksi.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTransaksi.size)
    }
    
    inner class ItemCheckoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCheckoutBinding.bind(itemView)
        fun bind(transaksi: Transaksi) {
            ikanHelper = IkanHelper.getInstance(itemView.context)
            ikanHelper.open()
            val cursor = ikanHelper.queryById(transaksi.idIkan.toString())
            MappingHelper.mapIkanCursorToIkan(cursor)?.also {
                binding.tvTitle.text = it.nama
            }
            binding.tvHarga.text = "Rp.${transaksi.total.toString()} (${transaksi.berat} Kg)"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCheckoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return ItemCheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCheckoutViewHolder, position: Int) {
        holder.bind(listTransaksi[position])
    }

    override fun getItemCount(): Int = this.listTransaksi.size
}