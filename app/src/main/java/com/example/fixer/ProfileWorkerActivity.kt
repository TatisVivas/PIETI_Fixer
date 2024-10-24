package com.example.fixer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.fixer.databinding.ActivityProfileWorkerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileWorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileWorkerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid
        userRef = database.getReference("workers").child(userId!!)


        binding.buttonCerrarSesion.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.editarFotoBoton.setOnClickListener {
            startActivity(Intent(this, ProfilePhotoActivity::class.java))
        }
        binding.volverAlMenu.setOnClickListener {
            startActivity(Intent(this, WorkerActivity::class.java))
        }
        binding.buttonVerHistorial.setOnClickListener {
            startActivity(Intent(this, HistoryServicesActivity::class.java))
        }
        // Load image URI from SharedPreferences
        loadImageUri()

        // Fetch user data from Firebase Realtime Database
        fetchUserData()

    }

    private fun fetchUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val nombre = dataSnapshot.child("nombre").getValue(String::class.java) ?: "Name not available"
                    val apellido = dataSnapshot.child("apellido").getValue(String::class.java) ?: "Last name not available"
                    val correo = dataSnapshot.child("correo").getValue(String::class.java) ?: "Email not available"
                    val telefono = dataSnapshot.child("telefono").getValue(String::class.java) ?: "Phone number not available"

                    binding.textViewNombre.text = "$nombre $apellido"
                    binding.textViewCorreo.text = correo
                    binding.textViewTelefono.text = telefono
                    binding.textViewCalificacion.text = "4.9/5"
                    binding.textViewNumServicios.text = "2"
                } else {
                    // Handle case where user data does not exist
                    binding.textViewNombre.text = "Name not available"
                    binding.textViewCorreo.text = "Email not available"
                    binding.textViewTelefono.text = "Phone number not available"
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
            //Glide.with(this).load(uri).into(binding.imageViewPerfil)
            binding.imageViewPerfil.setImageDrawable(resources.getDrawable(R.drawable.baseline_person_24))
        }
    }

}