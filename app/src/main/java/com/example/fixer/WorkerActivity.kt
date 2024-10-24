package com.example.fixer

import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fixer.databinding.ActivityWorkerBinding

class WorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activityButton.setOnClickListener {
            startActivity(Intent(baseContext, WorkerPageActivity::class.java))
        }
        binding.profileButton.setOnClickListener {
            startActivity(Intent(baseContext, ProfileWorkerActivity::class.java))
        }
        binding.acceptButton.setOnClickListener {
            Toast.makeText(this, "respuesta de aceptación enviada", Toast.LENGTH_SHORT).show()
            binding.acceptButton.isEnabled = false
            binding.rejectButton.isEnabled = false
            binding.acceptButton.setBackgroundColor(parseColor("#808080"))
            binding.rejectButton.setBackgroundColor(parseColor("#808080"))
        }
        binding.rejectButton.setOnClickListener {
            Toast.makeText(this, "respuesta de rechazo enviada", Toast.LENGTH_SHORT).show()
            binding.acceptButton.isEnabled = false
            binding.rejectButton.isEnabled = false
            binding.acceptButton.setBackgroundColor(parseColor("#808080"))
            binding.rejectButton.setBackgroundColor(parseColor("#808080"))
        }
    }
}