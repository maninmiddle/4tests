package com.example.a4tests.ui.tests_list

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.a4tests.R
import com.example.a4tests.adapter.TestsListAdapter
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTestsListBinding
import com.example.a4tests.databinding.IdDialogBinding
import com.example.a4tests.instances.RetrofitInstance
import com.example.a4tests.model.TaskModel
import com.example.a4tests.model.TestModel
import com.example.a4tests.model.VariantModel
import com.example.a4tests.ui.tests_confirm.TestsConfirmActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestsListActivity : BaseActivity() {

    private lateinit var bind: ActivityTestsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityTestsListBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.testsListRecycler.layoutManager =
            LinearLayoutManager(this)

        RetrofitInstance.apiMethods.getTestsList().enqueue(object : Callback<List<TestModel>> {
            override fun onResponse(
                call: Call<List<TestModel>>,
                response: Response<List<TestModel>>
            ) {
                if (response.body() != null && response.body()!!.isNotEmpty()) {
                    bind.testsListRecycler.adapter =
                        TestsListAdapter(
                            this@TestsListActivity,
                            response.body()!!
                        )
                } else {
                    showPlug(
                        getString(R.string.stringNoTests),
                        ContextCompat.getDrawable(
                            this@TestsListActivity,
                            R.drawable.ic_sad
                        )
                    )
                }

                bind.testsListLoader.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<TestModel>>, t: Throwable) {
                println(t.localizedMessage)

                showPlug(
                    getString(R.string.stringError),
                    ContextCompat.getDrawable(
                        this@TestsListActivity,
                        R.drawable.ic_no_connection
                    )
                )

                bind.testsListLoader.visibility = View.GONE
            }
        })

        bind.testsListSearch.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogBind = IdDialogBinding.bind(
                LayoutInflater
                    .from(this)
                    .inflate(
                        R.layout.id_dialog,
                        bind.root,
                        false
                    )
            )

            dialogBind.idConfirm.setOnClickListener {
                val id = dialogBind.idInput.text.toString()
                if (id.isNotEmpty()) {
                    RetrofitInstance.apiMethods.getTestById(id.toInt())
                        .enqueue(object : Callback<MutableList<TestModel>> {
                            override fun onResponse(
                                call: Call<MutableList<TestModel>>,
                                response: Response<MutableList<TestModel>>
                            ) {
                                if (response.body()!!.size > 0) {
                                    val test = response.body()!![0]

                                    RetrofitInstance.apiMethods.getTasks(id.toInt())
                                        .enqueue(object : Callback<MutableList<TaskModel>> {
                                            override fun onResponse(
                                                call: Call<MutableList<TaskModel>>,
                                                response: Response<MutableList<TaskModel>>
                                            ) {
                                                var totalTasks = 0
                                                test.tasks = response.body()!!

                                                for (task in test.tasks)
                                                    RetrofitInstance.apiMethods.getVariants(task.id)
                                                        .enqueue(object :
                                                            Callback<MutableList<VariantModel>> {
                                                            override fun onResponse(
                                                                call: Call<MutableList<VariantModel>>,
                                                                response: Response<MutableList<VariantModel>>
                                                            ) {
                                                                task.variantsModel =
                                                                    response.body()!!

                                                                UserData.targetTest = test

                                                                totalTasks++
                                                                if (totalTasks == test.tasks.size)
                                                                    startActivity(
                                                                        Intent(
                                                                            this@TestsListActivity,
                                                                            TestsConfirmActivity::class.java
                                                                        ).putExtra(
                                                                            "isTestStart",
                                                                            true
                                                                        )
                                                                    )
                                                            }

                                                            override fun onFailure(
                                                                call: Call<MutableList<VariantModel>>,
                                                                t: Throwable
                                                            ) {
                                                                println(t.localizedMessage)

                                                                Toast.makeText(
                                                                    this@TestsListActivity,
                                                                    getString(R.string.stringError),
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        })
                                            }

                                            override fun onFailure(
                                                call: Call<MutableList<TaskModel>>,
                                                t: Throwable
                                            ) {
                                                println(t.localizedMessage)

                                                Toast.makeText(
                                                    this@TestsListActivity,
                                                    getString(R.string.stringError),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                                } else
                                    Toast.makeText(
                                        this@TestsListActivity,
                                        getString(R.string.stringNotFoundTest),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                dialog.dismiss()
                            }

                            override fun onFailure(
                                call: Call<MutableList<TestModel>>,
                                t: Throwable
                            ) {
                                println(t.localizedMessage)
                            }
                        })
                }
            }

            dialogBind.idCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.apply {
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setView(dialogBind.root)
                show()
            }
        }

        bind.testsListBack.setOnClickListener {
            finish()
        }
    }

    private fun showPlug(text: String, image: Drawable?) {
        bind.testsListPlugText.visibility = View.VISIBLE
        bind.testsListPlugImage.visibility = View.VISIBLE

        Glide
            .with(this)
            .load(image)
            .into(bind.testsListPlugImage)

        bind.testsListPlugText.text = text
    }
}