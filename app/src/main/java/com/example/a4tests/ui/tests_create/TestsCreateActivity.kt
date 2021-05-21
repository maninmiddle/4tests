package com.example.a4tests.ui.tests_create

import android.os.Bundle
import com.example.a4tests.R
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsCreateBinding
import com.example.a4tests.model.TaskModel
import com.example.a4tests.model.TestModel
import com.example.a4tests.ui.tasks_create.TasksCreateActivity

class TestsCreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = ActivityTestsCreateBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.testsCreateContinue.setOnClickListener {
            val name = bind.testsCreateName.text.toString()
            val direction = bind.testsCreateDirection.text.toString()
            val completeTime = bind.testsCreateCompleteTime.text.toString()
            val password = bind.testsCreatePassword.text.toString()

            when {
                name.isEmpty() ->
                    bind.testsCreateName.error = getString(R.string.stringEnterName)

                direction.isEmpty() ->
                    bind.testsCreateDirection.error = getString(R.string.stringEnterDirection)

                completeTime.isEmpty() ->
                    bind.testsCreateCompleteTime.error = getString(R.string.stringEnterCompleteTime)

                else -> {
                    UserData.targetTest = TestModel(
                        name,
                        direction,
                        completeTime.toInt(),
                        password,
                        mutableListOf(
                            TaskModel("", mutableListOf("", ""), mutableListOf())
                        )
                    )

                    startActivity(TasksCreateActivity::class.java)
                }
            }
        }

        bind.testsCreateBack.setOnClickListener {
            finish()
        }
    }
}