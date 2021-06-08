package com.example.a4tests.ui.tests_result

import android.os.Bundle
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsResultBinding
import java.util.*
import kotlin.math.roundToInt

class TestsResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = ActivityTestsResultBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val tasks = UserData.targetTest.tasks
        val rightAnswers = intent.extras!!.getInt("rightAnswers")

        bind.testsResultMark.text =
            ((rightAnswers * 100) / (tasks.size * 10)).toFloat().roundToInt().toString()

        bind.testsResultTasks.text =
            String.format(
                Locale.getDefault(),
                "%s\n%d",
                bind.testsResultTasks.text,
                tasks.size
            )

        bind.testsResultRightAnswers.text =
            String.format(
                Locale.getDefault(),
                "%s\n%d",
                bind.testsResultRightAnswers.text,
                rightAnswers
            )

        bind.testsResultBack.setOnClickListener {
            finish()
        }
    }
}