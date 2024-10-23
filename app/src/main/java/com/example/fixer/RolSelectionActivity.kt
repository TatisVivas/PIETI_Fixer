package com.example.fixer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fixer.databinding.ActivityRolSelectionBinding

class RolSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRolSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRolSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener para confirmar el rol seleccionado y pasar a la pantalla de registro
        binding.confirmar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            // Determina el rol según la selección del usuario en el layout
            val rol = if (binding.rolusuario.isChecked) "customer" else "worker"
            intent.putExtra("rol", rol)

            // Inicia la pantalla de registro con el rol seleccionado
            startActivity(intent)
        }
    }
}
