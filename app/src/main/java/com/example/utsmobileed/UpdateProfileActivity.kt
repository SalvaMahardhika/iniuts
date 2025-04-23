package com.example.utsmobileed

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        db = AppDatabase.getDatabase(this)
        user = intent.getSerializableExtra("user") as User

        findViewById<EditText>(R.id.edtName).setText(user.name)
        findViewById<EditText>(R.id.edtPhone).setText(user.phone)
        findViewById<EditText>(R.id.edtAddress).setText(user.address)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val updatedUser = user.copy(
                name = findViewById<EditText>(R.id.edtName).text.toString(),
                phone = findViewById<EditText>(R.id.edtPhone).text.toString(),
                address = findViewById<EditText>(R.id.edtAddress).text.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().update(updatedUser)
                withContext(Dispatchers.Main) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("user", updatedUser)
                    setResult(RESULT_OK, resultIntent)
                    Toast.makeText(this@UpdateProfileActivity, "Profil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
