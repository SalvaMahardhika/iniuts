package com.example.utsmobileed

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = AppDatabase.getDatabase(this)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val name = findViewById<EditText>(R.id.edtName).text.toString()
            val email = findViewById<EditText>(R.id.edtEmail).text.toString()
            val password = findViewById<EditText>(R.id.edtPassword).text.toString()
            val phone = findViewById<EditText>(R.id.edtPhone).text.toString()
            val address = findViewById<EditText>(R.id.edtAddress).text.toString()

            val user = User(name = name, email = email, password = password, phone = phone, address = address)

            // Menyimpan pengguna ke database
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().register(user)
                withContext(Dispatchers.Main) {
                    // Tampilkan pesan setelah berhasil mendaftar
                    Toast.makeText(this@RegisterActivity, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                    finish() // Tutup activity dan kembali ke LoginActivity
                }
            }
        }
    }
}
