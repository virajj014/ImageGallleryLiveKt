package com.example.imagegallerylive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagegallerylive.databinding.ActivityImageFullPageBinding
import com.example.imagegallerylive.databinding.ActivityMainBinding

class ImageFullPage : AppCompatActivity() {

    private lateinit var binding: ActivityImageFullPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageFullPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imagePath = intent.getStringExtra("path")
        val imageName = intent.getStringExtra("name")
        supportActionBar?.setTitle(imageName)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
            .load(imagePath)
            .apply(RequestOptions())
            .into(binding.imageFullCard)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Navigate back when the up button is pressed
        return true
    }
}