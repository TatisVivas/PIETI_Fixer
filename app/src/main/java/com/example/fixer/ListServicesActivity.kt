package com.example.fixer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fixer.databinding.ActivityListServicesBinding

class ListServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListServicesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.servicesButton.setOnClickListener {
            startActivity(Intent(baseContext, CustomerActivity::class.java))
        }
        binding.profileButton.setOnClickListener {
            startActivity(Intent(baseContext, ProfileCustomerActivity::class.java))
        }
        binding.calificarServicio.setOnClickListener {
            val intent = Intent(this, RateServiceActivity::class.java)
            intent.putExtra("workerImageResId", R.drawable.johndoe) // Replace with actual image resource ID
            intent.putExtra("serviceDescription", "Descripción del servicio")
            startActivity(intent)
        }
        binding.volverAlMenu.setOnClickListener {
            startActivity(Intent(baseContext, CustomerActivity::class.java))
        }
    }
}