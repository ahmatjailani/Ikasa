package com.pember.praktikum.ui.home.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pember.praktikum.R
import com.pember.praktikum.data.Ikan
import com.pember.praktikum.databinding.ItemCartBinding
import com.pember.praktikum.db.IkanHelper
import com.pember.praktikum.ui.admin.EditIkanActivity
import com.pember.praktikum.utils.Utils


class ItemIkanAdapter : RecyclerView.Adapter<ItemIkanAdapter.ItemCartViewHolder>() {
    private lateinit var ikanHelper: IkanHelper

    var ListIkan = ArrayList<Ikan>()
        set(ListIkan) {
            if (ListIkan.size > 0) {
                this.ListIkan.clear()
            }
            this.ListIkan.addAll(ListIkan)
        }

    fun addItem(ikan: Ikan) {
        this.ListIkan.add(ikan)
        notifyItemInserted(this.ListIkan.size - 1)
    }

    fun updateItem(position: Int, ikan: Ikan) {
        this.ListIkan[position] = ikan
        notifyItemChanged(position, ikan)
    }

    fun removeItem(position: Int) {
        this.ListIkan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.ListIkan.size)
    }

    inner class ItemCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCartBinding.bind(itemView)
        fun bind(ikan: Ikan) {
            binding.tvNama.text = ikan.nama
            binding.tvHarga.text = "Rp. ${ikan.harga.toString()} /kg"
            binding.tvStatus.text = "Stock ${ikan.stock}"
            binding.ivItem.setImageBitmap(Utils.getImage(ikan.gambar))

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EditIkanActivity::class.java)
                intent.putExtra(EditIkanActivity.ID, ikan.id)
                intent.putExtra(EditIkanActivity.NAMA, ikan.nama)
                intent.putExtra(EditIkanActivity.DESC, ikan.deskripsi)
                intent.putExtra(EditIkanActivity.HARGA, ikan.harga)
                intent.putExtra(EditIkanActivity.STOK, ikan.stock)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ItemCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        holder.bind(ListIkan[position])
    }

    override fun getItemCount(): Int = this.ListIkan.size

}