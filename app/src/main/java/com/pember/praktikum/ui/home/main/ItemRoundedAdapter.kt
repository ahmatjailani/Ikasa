package com.pember.praktikum.ui.home.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pember.praktikum.R
import com.pember.praktikum.data.Ikan
import com.pember.praktikum.databinding.ItemRoundedBinding
import com.pember.praktikum.utils.Utils

class ItemRoundedAdapter: RecyclerView.Adapter<ItemRoundedAdapter.ItemRoundedViewHolder>() {
    var listIkan = ArrayList<Ikan>()
        set(listIkan) {
            if (listIkan.size > 0) {
                this.listIkan.clear()
            }
            this.listIkan.addAll(listIkan)
        }
    fun addItem(ikan: Ikan) {
        this.listIkan.add(ikan)
        notifyItemInserted(this.listIkan.size - 1)
    }
    fun updateItem(position: Int, ikan: Ikan) {
        this.listIkan[position] = ikan
        notifyItemChanged(position, ikan)
    }
    fun removeItem(position: Int) {
        this.listIkan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listIkan.size)
    }
    inner class ItemRoundedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRoundedBinding.bind(itemView)
        fun bind(ikan: Ikan) {
            binding.textView.text = ikan.nama
            binding.imageView.setImageBitmap(Utils.getImage(ikan.gambar))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRoundedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rounded, parent, false)
        return ItemRoundedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRoundedViewHolder, position: Int) {
        holder.bind(listIkan[position])
    }

    override fun getItemCount(): Int = this.listIkan.size
}