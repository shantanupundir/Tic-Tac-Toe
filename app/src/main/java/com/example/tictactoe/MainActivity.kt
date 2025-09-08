package com.example.tictactoe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainer.visibility = View.GONE

        binding.btnPlay.setOnClickListener {
            val player1: String = binding.player1.text.toString().trim()
            val player2: String = binding.player2.text.toString().trim()

            if (player1.isEmpty() || player2.isEmpty()) {
                binding.player1.error = if (player1.isEmpty()) "Enter Player 1 Name" else null
                binding.player2.error = if (player2.isEmpty()) "Enter Player 2 Name" else null
                return@setOnClickListener
            }
            binding.title.visibility = View.GONE
            binding.player1.visibility = View.GONE
            binding.player2.visibility = View.GONE
            binding.btnPlay.visibility = View.GONE

            binding.fragmentContainer.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, MainFragment())
                .commit()
        }
    }
}
