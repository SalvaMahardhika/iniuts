package com.example.utsmobileed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Langsung pindah ke LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Tutup MainActivity agar tidak bisa kembali ke sini
        finish()
    }
}
