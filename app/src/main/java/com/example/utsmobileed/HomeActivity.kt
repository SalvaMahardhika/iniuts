package com.example.utsmobileed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var user: User

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedUser = result.data?.getSerializableExtra("user") as? User
            if (updatedUser != null) {
                user = updatedUser
                showUserData(user)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = intent.getSerializableExtra("user") as User
        showUserData(user)

        // Tombol Update Profile
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("user", user)
            launcher.launch(intent)
        }

        // Tombol Logout
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun showUserData(user: User) {
        findViewById<TextView>(R.id.txtName).text = user.name
        findViewById<TextView>(R.id.txtEmail).text = user.email
        findViewById<TextView>(R.id.txtPhone).text = user.phone
        findViewById<TextView>(R.id.txtAddress).text = user.address
    }
}
