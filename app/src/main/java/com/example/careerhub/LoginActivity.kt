package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val tvForgot = findViewById<TextView>(R.id.tvForgotPassword)
        progress = findViewById(R.id.progressBar)

        // ✅ LOAD SAVED CREDENTIALS
        val prefs = getSharedPreferences("login_prefs", MODE_PRIVATE)
        etEmail.setText(prefs.getString("email", ""))
        etPassword.setText(prefs.getString("password", ""))

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                toast("Enter valid email")
                return@setOnClickListener
            }
            if (pass.length < 6) {
                toast("Password must be 6+ characters")
                return@setOnClickListener
            }

            askToSaveCredentials(email, pass)
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgot.setOnClickListener { showResetDialog() }
    }

    private fun askToSaveCredentials(email: String, pass: String) {
        AlertDialog.Builder(this)
            .setTitle("Save Login?")
            .setMessage("Do you want to save email & password for next time?")
            .setPositiveButton("YES") { _, _ ->
                val prefs = getSharedPreferences("login_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putString("email", email)
                    .putString("password", pass)
                    .apply()
                login(email, pass)
            }
            .setNegativeButton("NO") { _, _ ->
                login(email, pass)
            }
            .show()
    }

    private fun login(email: String, pass: String) {
        progress.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                toast("Login failed")
            }
            .addOnCompleteListener {
                progress.visibility = View.GONE
            }
    }

    private fun showResetDialog() {
        val input = EditText(this)
        input.hint = "Registered email"

        AlertDialog.Builder(this)
            .setTitle("Reset Password")
            .setView(input)
            .setPositiveButton("Send") { _, _ ->
                auth.sendPasswordResetEmail(input.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
