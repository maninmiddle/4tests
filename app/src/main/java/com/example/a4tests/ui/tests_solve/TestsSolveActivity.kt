package com.example.a4tests.ui.tests_solve

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4tests.R
import com.example.a4tests.adapter.TestsSolveAdapter
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsSolveBinding
import com.example.a4tests.ui.main.MainActivity
import kotlin.math.roundToInt

class TestsSolveActivity : BaseActivity() {

    var currentTask = -1
    var rightAnswers = -1
    lateinit var bind: ActivityTestsSolveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityTestsSolveBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.testsSolveTaskRecycler.layoutManager =
            LinearLayoutManager(this)

        bind.testsSolveBack.setOnClickListener {
            finish()
        }

        answer(true)
    }

    fun answer(isRight: Boolean) {
        currentTask++

        if (isRight)
            rightAnswers++

        if (currentTask == UserData.targetTest.tasks.size) {
            val tasks = UserData.targetTest.tasks
            Toast.makeText(this, "Правильных ответов: $rightAnswers\nВаша оценка: ${((rightAnswers * 100) / (tasks.size * 10)).toFloat().roundToInt()}", Toast.LENGTH_SHORT).show()

            startActivity(MainActivity::class.java)
            finishAffinity()
        } else {
            val task = UserData.targetTest.tasks[currentTask]
            bind.testsSolveTitle.text = "${getString(R.string.stringQuestionNumber)} ${currentTask + 1}"
            bind.testsSolveTaskText.text = task.text
            bind.testsSolveTaskRecycler.adapter =
                TestsSolveAdapter(this, task.variantsModel)
        }
    }
}