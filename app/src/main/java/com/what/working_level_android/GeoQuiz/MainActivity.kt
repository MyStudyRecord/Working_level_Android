package com.what.working_level_android.GeoQuiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.what.working_level_android.R
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    lateinit var questionTextView: TextView

    private val KEY_INDEX = "index"
    // 최초로 quizViewModel이 사용될 때까지 초기화를 늦춤
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("로그","onSvaeInstanceState")
        outState.putInt(KEY_INDEX,quizViewModel.currentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        questionTextView = findViewById<TextView>(R.id.question_text_view)
        val previousButton = findViewById<Button>(R.id.previous_button)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?:0
        quizViewModel.currentIndex = currentIndex
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
            if (quizViewModel.currentIndex <= 0) {
                Toast.makeText(this, "더이상 뒤로 갈수 없습니다", Toast.LENGTH_SHORT).show()
            } else {
                previousQuestion()
            }
        }

    }

    private fun nextQuestion() {
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun previousQuestion() {
        quizViewModel.moveToPrevious()
        updateQuestion()
    }

    private fun updateQuestion() {
        Log.d("로그","Updating question text",Exception())
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}