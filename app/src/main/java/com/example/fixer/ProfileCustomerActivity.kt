package com.example.fixer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fixer.databinding.ActivityProfileCustomerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.bumptech.glide.Glide

class ProfileCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileCustomerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid
        userRef = database.getReference("customers").child(userId!!)

        binding.cerrarSesion.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.botonEditarFoto.setOnClickListener {
            startActivity(Intent(this, ProfilePhotoActivity::class.java))
        }
        binding.volverAlMenu.setOnClickListener {
            startActivity(Intent(this, CustomerActivity::class.java))
        }

        // Fetch user data from Firebase Realtime Database
        fetchUserData()

        // Load image URI from SharedPreferences
        loadImageUri()
    }

    private fun fetchUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val nombre = dataSnapshot.child("nombre").getValue(String::class.java) ?: "Name not available"
                    val apellido = dataSnapshot.child("apellido").getValue(String::class.java) ?: "Last name not available"
                    val correo = dataSnapshot.child("correo").getValue(String::class.java) ?: "Email not available"
                    val telefono = dataSnapshot.child("telefono").getValue(String::class.java) ?: "Phone number not available"

                    binding.username.text = "$nombre $apellido"
                    binding.correoEditar.text = correo
                    binding.numeroEditar.text = telefono
                } else {
                    // Handle case where user data does not exist
                    binding.username.text = "Name not available"
                    binding.correoEditar.text = "Email not available"
                    binding.numeroEditar.text = "Phone number not available"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun loadImageUri() {
        val sharedPreferences = getSharedPreferences("ProfilePhotoPrefs", Context.MODE_PRIVATE)
        val imageUri = sharedPreferences.getString("imageUri", null)
        if (imageUri != null) {
            val uri = Uri.parse(imageUri)
            Glide.with(this).load(uri).into(binding.userPhoto)
        }
    }
}