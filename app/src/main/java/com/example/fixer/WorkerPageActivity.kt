package com.example.fixer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fixer.databinding.ActivityWorkerPageBinding

class WorkerPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkerPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.volverAlMenu.setOnClickListener{
            startActivity(Intent(baseContext, WorkerActivity::class.java))
        }
        binding.profileButton.setOnClickListener{
            startActivity(Intent(baseContext, ProfileWorkerActivity::class.java))
        }
    }
}