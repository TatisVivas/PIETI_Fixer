package com.example.fixer

import android.os.Bundle
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
    }
}