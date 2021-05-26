package com.what.working_level_android.GeoQuiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.what.working_level_android.R

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)

    )
    private var currentIndex = 0

    lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        questionTextView = findViewById<TextView>(R.id.question_text_view)
        val previousButton = findViewById<Button>(R.id.previous_button)

        Log.d("로그", "현재 : $currentIndex")
        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        updateQuestion()

        nextButton.setOnClickListener {
            nextQuestion()

        }
        questionTextView.setOnClickListener {

            nextQuestion()

        }
        previousButton.setOnClickListener {
            if (currentIndex <= 0) {
                Toast.makeText(this, "더이상 뒤로 갈수 없습니다", Toast.LENGTH_SHORT).show()
            } else {
                previousQuestion()
            }
        }

    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun previousQuestion() {
        currentIndex = (currentIndex - 1)
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}