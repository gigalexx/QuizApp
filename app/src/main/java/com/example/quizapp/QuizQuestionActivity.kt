package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition = 1
    private var mSelectedOptionPosition = 0
    private var mQuestionsList: ArrayList<Question>? = null
    private var mCorrectAnswers = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constans.USER_NAME)

        mQuestionsList = Constans.getQuestions()

        opt_one.setOnClickListener(this)
        opt_two.setOnClickListener(this)
        opt_three.setOnClickListener(this)
        opt_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition - 1]

        setDefaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) btn_submit.text =
            "FINISH" else btn_submit.text = "SUBMIT"

        my_progressBar.progress = mCurrentPosition
        iv_image.setImageResource(question.image)
        tv_question.text = question.question
        opt_one.text = question.optionOne
        opt_two.text = question.optionTwo
        opt_three.text = question.optionThree
        opt_four.text = question.optionFour

    }

    private fun setDefaultOptionsView() {

        val options = ArrayList<TextView>(4)
        options.add(0, opt_one)
        options.add(1, opt_two)
        options.add(2, opt_three)
        options.add(3, opt_four)


        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            opt_one -> selectedOptionView(opt_one, 1)
            opt_two -> selectedOptionView(opt_two, 2)
            opt_three -> selectedOptionView(opt_three, 3)
            opt_four -> selectedOptionView(opt_four, 4)
            btn_submit -> handleButtonClick()
        }
    }

    private fun handleButtonClick() {
        if (mSelectedOptionPosition == 0) {
            mCurrentPosition++

            if (mCurrentPosition <= mQuestionsList!!.size) {
                setQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constans.USER_NAME, mUserName)
                intent.putExtra(Constans.CORRECT_ANSWERS, mCorrectAnswers)
                intent.putExtra(Constans.TOTAL_QUESTIONS, mQuestionsList!!.size)
                startActivity(intent)
                finish()
            }
        } else {
            val question = mQuestionsList?.get(mCurrentPosition - 1)
            if (question?.correctAnswer != mSelectedOptionPosition) answerView(
                mSelectedOptionPosition,
                R.drawable.incorrect_option_border_bg
            ) else mCorrectAnswers++
            answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)
            if (mCurrentPosition == mQuestionsList!!.size) btn_submit.text =
                "FINISH" else btn_submit.text = "GO TO NEXT QUESTION"

            mSelectedOptionPosition = 0
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        setDefaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }

    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> opt_one.background = ContextCompat.getDrawable(this, drawableView)
            2 -> opt_two.background = ContextCompat.getDrawable(this, drawableView)
            3 -> opt_three.background = ContextCompat.getDrawable(this, drawableView)
            4 -> opt_four.background = ContextCompat.getDrawable(this, drawableView)

        }

    }
}