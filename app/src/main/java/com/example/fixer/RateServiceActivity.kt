package com.example.fixer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fixer.databinding.ActivityRateServiceBinding

class RateServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRateServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitRatingButton.setOnClickListener {
            val rating = binding.ratingBar.rating
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
            startActivity(Intent(baseContext, CustomerActivity::class.java))
        }


    }
}