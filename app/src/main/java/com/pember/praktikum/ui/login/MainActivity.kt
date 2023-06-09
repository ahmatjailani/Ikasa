package com.pember.praktikum.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pember.praktikum.databinding.ActivityMainBinding
import com.pember.praktikum.db.DatabaseContract
import com.pember.praktikum.db.LoginHelper
import com.pember.praktikum.db.UserHelper
import com.pember.praktikum.ui.admin.FormIkanActivity
import com.pember.praktikum.ui.home.main.HomeActivity
import com.pember.praktikum.ui.home.pesanan.PesananProsesFragment
import com.pember.praktikum.ui.home.pesanan.PesananSelesaiFragment
import com.pember.praktikum.ui.home.pesanan.PesananSemuaFragment
import com.pember.praktikum.ui.register.Register
import com.pember.praktikum.utils.MappingHelper

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userHelper: UserHelper
    private lateinit var loginHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide();
        setContentView(binding.root)
        userHelper = UserHelper.getInstance(this)
        loginHelper = LoginHelper.getInstance(this)
        userHelper.open()
        loginHelper.open()

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val cursor = userHelper.queryLogin(binding.etEmail.text.toString().trim(), binding.etPassword.text.toString().trim())
        MappingHelper.mapUserCursorToUser(cursor).also {
            if (it != null) {
                if (binding.etPassword.text.toString().trim() == it.password) {
                    when (it.role){
                        0 -> {
                            val values = ContentValues()
                            values.put(DatabaseContract.LoginColumns.ID_USER, it.id)
                            values.put(DatabaseContract.LoginColumns.NAMA_USER, it.nama)
                            values.put(DatabaseContract.LoginColumns.ROLE, it.role)
                            val result = loginHelper.insert(values)
                            if (result > 0) {
                                userHelper.close()
                                loginHelper.close()

                                PesananSelesaiFragment.ID_USER = it.id.toString()
                                PesananSemuaFragment.ID_USER = it.id.toString()
                                PesananProsesFragment.ID_USER = it.id.toString()

                                intent = Intent(this, HomeActivity::class.java)
                                intent.addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                )
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Gagal login", Toast.LENGTH_SHORT).show()
                            }
                        }
                        1 -> {
                            val values = ContentValues()
                            values.put(DatabaseContract.LoginColumns.ID_USER, it.id)
                            values.put(DatabaseContract.LoginColumns.NAMA_USER, it.nama)
                            values.put(DatabaseContract.LoginColumns.ROLE, it.role)
                            val result = loginHelper.insert(values)
                            if (result > 0) {
                                userHelper.close()
                                loginHelper.close()
                                intent = Intent(this, FormIkanActivity::class.java)
                                intent.addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                )
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Gagal login", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Anda belum terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cekLogin(){
        val cursor = loginHelper.queryAll()
        MappingHelper.mapLoginCursorToArrayList(cursor).also {
            if (it.size > 0) {
                if (it[0].role == 0) {
                    intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                    PesananSelesaiFragment.ID_USER = it[0].id_user.toString()
                    PesananSemuaFragment.ID_USER = it[0].id_user.toString()
                    PesananProsesFragment.ID_USER = it[0].id_user.toString()
                    startActivity(intent)
                }else{
                    intent = Intent(this, FormIkanActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                    startActivity(intent)
                }
            }
        }
    }
}