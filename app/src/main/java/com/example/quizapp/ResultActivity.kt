package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        tv_user_name.text = intent.getStringExtra(Constans.USER_NAME)

        val score  = intent.getIntExtra(Constans.CORRECT_ANSWERS,0)
        val totalQuestions  = intent.getIntExtra(Constans.TOTAL_QUESTIONS,0)

        tv_score.text = "Your score is $score out of $totalQuestions!"

        btn_finish.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}