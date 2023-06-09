package com.pember.praktikum.ui.FormPesan

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.pember.praktikum.utils.MappingHelper
import com.pember.praktikum.data.Ikan
import com.pember.praktikum.databinding.ActivityFormPesanBinding
import com.pember.praktikum.db.CartHelper
import com.pember.praktikum.db.DatabaseContract
import com.pember.praktikum.db.LoginHelper
import com.pember.praktikum.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FormPesanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormPesanBinding
    private lateinit var cartHelper: CartHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPesanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<Ikan>("ikan")

        binding.tvTitle.text = data?.nama
        binding.tvHarga.text = data?.harga.toString()
        binding.tvDesc.text = data?.deskripsi
        binding.imageView2.setImageBitmap(data?.gambar?.let { Utils.getImage(it) })
        binding.etJumlah.apply {
            setText("1")
            isEnabled = false
        }
        binding.btnMinus.setOnClickListener {
            if (binding.etJumlah.text.toString().toInt() > 1) {
                binding.etJumlah.setText((binding.etJumlah.text.toString().toInt() - 1).toString())
            }
        }
        binding.btnPlus.setOnClickListener {
            binding.etJumlah.setText((binding.etJumlah.text.toString().toInt() + 1).toString())
        }

        cartHelper = CartHelper.getInstance(applicationContext)
        cartHelper.open()

        lifecycleScope.launch {
            val loginHelper = LoginHelper.getInstance(applicationContext)
            loginHelper.open()
            val idLogin  = async (Dispatchers.IO){
                val cursor = loginHelper.queryAll()
                MappingHelper.mapLoginCursorToArrayList(cursor).first().id_user.toString()
            }.await()
            binding.btnCart.setOnClickListener {
                val harga = data?.harga
                val id = data?.id
                val jumlah = binding.etJumlah.text.toString().toInt()
                val total = harga?.times(jumlah)

                val values = ContentValues()
                values.put(DatabaseContract.CartColumns.ID_USER, idLogin)
                values.put(DatabaseContract.CartColumns.ID_IKAN, id)
                values.put(DatabaseContract.CartColumns.BERAT, jumlah)
                values.put(DatabaseContract.CartColumns.TOTAL, total)
                val result = cartHelper.insert(values)
                if (result > 0) {
                    cartHelper.close()
                    Toast.makeText(this@FormPesanActivity, "Berhasil Masuk Ke Keranjang", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@FormPesanActivity, "Gagal membuat transaksi", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}