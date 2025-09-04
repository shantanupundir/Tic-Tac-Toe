package com.example.tictactoe

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.tictactoe.databinding.FragmentMainBinding
import com.example.tictactoe.databinding.WinDialogBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var currentPlayer = "X"
    private var board = Array(3) { Array(3){""} }
    private lateinit var buttons: Array<Array<Button>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons = arrayOf(
            arrayOf(binding.btn1,binding.btn2,binding.btn3),
            arrayOf(binding.btn4,binding.btn5,binding.btn6),
            arrayOf(binding.btn7,binding.btn8,binding.btn9)
        )
        for (i in 0..2){
            for (j in 0..2){
                buttons[i][j].setOnClickListener {
                    onCellClicked(i, j, buttons[i][j])
                }
            }
        }
    }
    private fun onCellClicked(row: Int, col: Int, button: Button) {
        if (board[row][col].isNotEmpty()) {
            Toast.makeText(requireContext(), "Cell already occupied!", Toast.LENGTH_SHORT).show()
            return
        }

        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWinner(currentPlayer)) {
            showWinnerDialog(currentPlayer)
            resetBoard()
            return
        }

        if (isBoardFull()) {
            Toast.makeText(requireContext(), "It's a draw!", Toast.LENGTH_LONG).show()
            resetBoard()
            return
        }

        // Switch Player
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    private fun checkWinner(player: String): Boolean {
        // Rows
        for (i in 0..2) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true
        }
        // Columns
        for (j in 0..2) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) return true
        }
        // Diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j].isEmpty()) return false
            }
        }
        return true
    }

    private fun resetBoard() {
        board = Array(3) { Array(3) { "" } }
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
        currentPlayer = "X"
    }
    private fun showWinnerDialog(winner: String) {
        // Binding से dialog का layout inflate करो
        val dialogBinding = WinDialogBinding.inflate(layoutInflater)

        // Winner का नाम set करो
        dialogBinding.winText.text = "Player $winner Wins 🎉"

        // AlertDialog बनाओ
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        // Cancel button → Dialog dismiss
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Restart button → Board reset + dialog dismiss
        dialogBinding.btnRestart.setOnClickListener {
            resetBoard()
            dialog.dismiss()
        }

        // Dialog show करो
        dialog.show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}