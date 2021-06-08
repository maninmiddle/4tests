package com.example.a4tests.ui.tests_confirm

import android.os.Bundle
import android.widget.Toast
import com.example.a4tests.R
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsConfirmBinding
import com.example.a4tests.instances.RetrofitInstance
import com.example.a4tests.model.VariantModel
import com.example.a4tests.ui.main.MainActivity
import com.example.a4tests.ui.tests_solve.TestsSolveActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TestsConfirmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = ActivityTestsConfirmBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val isTestStart = intent.getBooleanExtra("isTestStart", false)
        bind.testsConfirmTitle.text =
            if (isTestStart)
                getString(R.string.stringOpenTest)
            else
                getString(R.string.stringConfirm)

        bind.testsConfirmSubmit.text =
            if (isTestStart)
                getString(R.string.stringStart)
            else
                getString(R.string.stringCreateTest)

        val test = UserData.targetTest
        bind.testsConfirmName.text = test.name
        bind.testsConfirmDirection.text = test.subject
        bind.testsConfirmCompleteTime.text = test.completeTime.toString()
        bind.testsConfirmTasksCount.text = test.tasks.size.toString()

        bind.testsConfirmSubmit.setOnClickListener {
            if (isTestStart) {
                startActivityWithFinish(TestsSolveActivity::class.java)
            } else {
                RetrofitInstance.apiMethods.createTest(test).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val testId = response.body()!!

                        Toast.makeText(
                            this@TestsConfirmActivity,
                            String.format(
                                Locale.getDefault(),
                                "%s %s",
                                getString(R.string.stringTestCreated),
                                testId
                            ),
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(MainActivity::class.java)
                        finishAffinity()

                        var errorHandled = false
                        for (task in test.tasks) {
                            if (errorHandled)
                                break

                            task.testId = testId.toInt()

                            RetrofitInstance.apiMethods.createTask(task)
                                .enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        var variantNum = 0
                                        for (variant in task.variants) {
                                            if (errorHandled)
                                                break

                                            RetrofitInstance.apiMethods.createVariant(
                                                VariantModel(
                                                    response.body()!!.toInt(),
                                                    variant,
                                                    if (task.rightVariants.contains(variantNum)) 1 else 0
                                                )
                                            ).enqueue(object : Callback<Any> {
                                                override fun onResponse(
                                                    call: Call<Any>,
                                                    response: Response<Any>
                                                ) {
                                                }

                                                override fun onFailure(
                                                    call: Call<Any>,
                                                    t: Throwable
                                                ) {
                                                    println(t.localizedMessage)

                                                    Toast.makeText(
                                                        this@TestsConfirmActivity,
                                                        getString(R.string.stringError),
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    errorHandled = true
                                                }
                                            })

                                            variantNum++
                                        }
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        println(t.localizedMessage)

                                        Toast.makeText(
                                            this@TestsConfirmActivity,
                                            getString(R.string.stringError),
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        errorHandled = true
                                    }
                                })
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        println(t.localizedMessage)

                        Toast.makeText(
                            this@TestsConfirmActivity,
                            getString(R.string.stringError),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

        bind.testsConfirmBack.setOnClickListener {
            finish()
        }
    }
}