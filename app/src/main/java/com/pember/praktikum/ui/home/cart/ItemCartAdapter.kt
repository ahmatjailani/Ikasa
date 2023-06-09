package com.pember.praktikum.ui.home.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pember.praktikum.utils.MappingHelper
import com.pember.praktikum.R
import com.pember.praktikum.data.Cart
import com.pember.praktikum.databinding.ItemCheckoutBinding
import com.pember.praktikum.db.CartHelper
import com.pember.praktikum.db.IkanHelper
import com.pember.praktikum.utils.Utils

class ItemCartAdapter: RecyclerView.Adapter<ItemCartAdapter.ItemCartViewHolder>() {
    private lateinit var cartHelper: CartHelper
    private lateinit var ikanHelper: IkanHelper
    var ListCart = ArrayList<Cart>()
        set(ListCart) {
            if (ListCart.size > 0) {
                this.ListCart.clear()
            }
            this.ListCart.addAll(ListCart)
        }
    fun addItem(cart: Cart) {
        this.ListCart.add(cart)
        notifyItemInserted(this.ListCart.size - 1)
    }
    fun updateItem(position: Int, cart: Cart) {
        this.ListCart[position] = cart
        notifyItemChanged(position, cart)
    }
    fun removeItem(position: Int) {
        this.ListCart.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.ListCart.size)
    }

    fun removeAll() {
        this.ListCart.clear()
        notifyDataSetChanged()
    }

    inner class ItemCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCheckoutBinding.bind(itemView)
        fun bind(cart: Cart) {
            cartHelper = CartHelper.getInstance(itemView.context)
            cartHelper.open()
            ikanHelper = IkanHelper.getInstance(itemView.context)
            ikanHelper.open()
            val cursor = ikanHelper.queryById(cart.id_ikan.toString())
            MappingHelper.mapIkanCursorToIkan(cursor)?.also {
                binding.tvTitle.text = it.nama
                binding.ivItem.setImageBitmap(Utils.getImage(it.gambar))
                binding.tvHarga.text = "Rp.${cart.total} (${cart.jumlah} Kg)"
            }
            binding.btnDelete.setOnClickListener {
                val result = cartHelper.deleteById(cart.id.toString())
                if (result > 0) {
                    removeItem(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return ItemCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        holder.bind(ListCart[position])
    }

    override fun getItemCount(): Int = this.ListCart.size
}