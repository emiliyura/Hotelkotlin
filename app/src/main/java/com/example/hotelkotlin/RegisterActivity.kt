package com.example.hotelkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.btn_register)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val loginNow = findViewById<TextView>(R.id.loginNow)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = ProgressBar.VISIBLE

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = ProgressBar.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account created.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, com.example.hotelkotlin.MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        loginNow.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}