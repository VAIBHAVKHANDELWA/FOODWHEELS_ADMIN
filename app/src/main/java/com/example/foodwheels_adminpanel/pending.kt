package com.example.foodwheels_adminpanel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwheels.adapter.adapter1
import com.example.foodwheels_adminpanel.databinding.ActivityPendingBinding
import java.util.ArrayList

class pending : AppCompatActivity() {
    private val binding: ActivityPendingBinding by lazy {
        ActivityPendingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val cartfoodname = mutableListOf("Vivek", "Amit", "Suresh", "Ravi")
        val cartfoodimage = mutableListOf(R.drawable.burger, R.drawable.aloogobhi, R.drawable.bahji, R.drawable.pizza)
        val price = mutableListOf("100", "150", "200", "300")

        val adapter = adapter3(ArrayList(cartfoodname),ArrayList( price), ArrayList(cartfoodimage),this)
        binding.rcyl.layoutManager=LinearLayoutManager(this)
        binding.rcyl.adapter=adapter

    }
}