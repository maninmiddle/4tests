package com.example.a4tests.ui.tests_solve

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4tests.R
import com.example.a4tests.adapter.TestsSolveAdapter
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsSolveBinding
import com.example.a4tests.ui.main.MainActivity
import com.example.a4tests.ui.tests_result.TestsResultActivity

class TestsSolveActivity : BaseActivity() {

    private var currentTask = -1
    private var rightAnswers = -1
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
            startActivity(
                Intent(
                    this,
                    TestsResultActivity::class.java
                ).putExtra(
                    "rightAnswers",
                    rightAnswers
                )
            )

            finish()
        } else {
            val task = UserData.targetTest.tasks[currentTask]
            bind.testsSolveTitle.text =
                "${getString(R.string.stringQuestionNumber)} ${currentTask + 1}"
            bind.testsSolveTaskText.text = task.text
            bind.testsSolveTaskRecycler.adapter =
                TestsSolveAdapter(this, task.variantsModel)
        }
    }
}