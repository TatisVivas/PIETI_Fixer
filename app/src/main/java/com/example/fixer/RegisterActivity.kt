package com.example.fixer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fixer.databinding.ActivityRegisterBinding
import com.example.fixer.model.MyUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa Firebase Authentication y Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Recupera el rol de la pantalla anterior (customer o worker)
        val rol = intent.getStringExtra("rol") ?: "customer"

        // Evento del botón de registro
        binding.registroButtom.setOnClickListener {
            val nombre = binding.nombre.text.toString()
            val apellido = binding.apellido.text.toString()
            val telefono = binding.telefono.text.toString()
            val correo = binding.correo.text.toString()
            val contrasena = binding.contrasena.text.toString()

            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
                // Primero registrar al usuario en Firebase Authentication
                auth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Usuario registrado en Firebase Authentication con éxito
                            val firebaseUser = auth.currentUser
                            val userId = firebaseUser?.uid ?: return@addOnCompleteListener

                            // Crear el objeto MyUser con los datos adicionales
                            val user = MyUser(nombre, apellido, telefono, correo, contrasena, rol)

                            // Determinar la ruta en la base de datos según el rol
                            val rolePath = if (rol == "customer") "customers" else "workers"

                            // Guardar los datos adicionales en Firebase Realtime Database
                            database.child(rolePath).child(userId).setValue(user)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                        // Redirigir a la pantalla correspondiente después del registro
                                        if (rol == "customer") {
                                            startActivity(Intent(this, CustomerActivity::class.java))
                                        } else {
                                            startActivity(Intent(this, WorkerActivity::class.java))
                                        }
                                    } else {
                                        Toast.makeText(this, "Error al guardar los datos del usuario", Toast.LENGTH_SHORT).show()
                                    }
                                }

                        } else {
                            // Error al registrar en Firebase Authentication
                            Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor ingresa correo y contraseña válidos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para ir a la pantalla de iniciar sesión
        binding.iniciarsesionbutton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}