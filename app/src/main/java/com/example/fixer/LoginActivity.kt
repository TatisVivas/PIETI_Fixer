package com.example.fixer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fixer.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    val TAG = "FIREBASE_APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Verificar si el usuario ya está logueado
        if (auth.currentUser != null) {
            Log.d(TAG, "User already logged in, updating UI.")
            updateUI(auth.currentUser)
        }

        // Listener para el botón de registro
        binding.registerButton.setOnClickListener {
            startActivity(Intent(baseContext, RolSelectionActivity::class.java))
        }

        // Listener para el botón de iniciar sesión
        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            Log.d(TAG, "Botón iniciar sesión presionado")
            if (validateForm(email, password)) {
                Log.d(TAG, "Formulario validado, intentando iniciar sesión.")
                loginUser(email, password)
            }
        }
    }

    // Actualizar la UI si el usuario está autenticado
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val userId = currentUser.uid
            val customerRef = database.getReference("customers").child(userId)
            val workerRef = database.getReference("workers").child(userId)

            Log.d(TAG, "Attempting to retrieve user role for userId: $userId")

            customerRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d(TAG, "User is a 'customer', navigating to CustomerActivity.")
                        startActivity(Intent(baseContext, CustomerActivity::class.java))
                    } else {
                        workerRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.d(TAG, "User is a 'worker', navigating to WorkerActivity.")
                                    startActivity(Intent(baseContext, WorkerActivity::class.java))
                                } else {
                                    Log.d(TAG, "User role not found")
                                    Toast.makeText(baseContext, "User role not found", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e(TAG, "Error retrieving 'worker' role: ${databaseError.message}")
                            }
                        })
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(TAG, "Error retrieving 'customer' role: ${databaseError.message}")
                }
            })
        }
    }

    // Función para manejar el login con Firebase
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Login successful, updating UI.")
                Toast.makeText(baseContext, "Login successful!", Toast.LENGTH_SHORT).show()
                updateUI(auth.currentUser)
            } else {
                val message = it.exception?.message
                Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Login error: $message")
                binding.emailText.text.clear()
                binding.passwordText.text.clear()
            }
        }
    }

    // Validar el formulario antes de iniciar sesión
    private fun validateForm(email: String, password: String): Boolean {
        var valid = false
        if (email.isEmpty()) {
            binding.emailText.error = "Required!"
        } else if (!validEmailAddress(email)) {
            binding.emailText.error = "Invalid email address"
        } else if (password.isEmpty()) {
            binding.passwordText.error = "Required!"
        } else if (password.length < 6) {
            binding.passwordText.error = "Password should be at least 6 characters long!"
        } else {
            valid = true
        }
        return valid
    }

    // Validar formato de correo electrónico
    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(regex.toRegex())
    }
}