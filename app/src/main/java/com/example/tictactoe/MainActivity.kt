package com.example.tictactoe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // By default fragment container को hide रखते हैं
        binding.fragmentContainer.visibility = View.GONE

        // PLAY button click
        binding.btnPlay.setOnClickListener {
            val player1: String = binding.player1.text.toString().trim()
            val player2: String = binding.player2.text.toString().trim()

            if (player1.isEmpty() || player2.isEmpty()) {
                // अगर नाम खाली है तो Toast दिखा सकते हो
                binding.player1.error = if (player1.isEmpty()) "Enter Player 1 Name" else null
                binding.player2.error = if (player2.isEmpty()) "Enter Player 2 Name" else null
                return@setOnClickListener
            }

            // अब UI ke input fields को hide कर दो
            binding.title.visibility = View.GONE
            binding.player1.visibility = View.GONE
            binding.player2.visibility = View.GONE
            binding.btnPlay.visibility = View.GONE

            // Fragment container show करो
            binding.fragmentContainer.visibility = View.VISIBLE

            // Fragment load करो
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, MainFragment())
                .commit()
        }
    }
}
