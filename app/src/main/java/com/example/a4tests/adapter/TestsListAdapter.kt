package com.example.a4tests.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tests.R
import com.example.a4tests.animator.ViewAnimator
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.TestsListItemBinding
import com.example.a4tests.instances.RetrofitInstance
import com.example.a4tests.model.TaskModel
import com.example.a4tests.model.TestModel
import com.example.a4tests.model.VariantModel
import com.example.a4tests.ui.tests_confirm.TestsConfirmActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TestsListAdapter(
    private val context: Context,
    private val tests: List<TestModel>
) : RecyclerView.Adapter<TestsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.tests_list_item,
                    parent,
                    false
                )
        )

    override fun getItemCount() = tests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bind = TestsListItemBinding.bind(holder.itemView)
        val test = tests[position]
        val hours = (test.completeTime / 60)
        val minutes = test.completeTime - hours * 60

        if (!test.isAnimated) {
            ViewAnimator(bind.root).fadeIn(1000)

            if (position % 2 == 0)
                ViewAnimator(bind.root)
                    .translationX(1000, -100f, 0f)
            else
                ViewAnimator(bind.root)
                    .translationX(1000, 100f, 0f)

            test.isAnimated = true
        }

        bind.testName.text = test.name
        bind.testSubject.text = test.subject

        bind.testCompleteTime.text =
            if (hours > 0)
                String.format(
                    Locale.getDefault(),
                    "%d ч. %d мин.",
                    hours,
                    minutes
                )
            else
                String.format(
                    Locale.getDefault(),
                    "%d мин.",
                    minutes
                )

        bind.root.setOnClickListener {
            RetrofitInstance.apiMethods.getTasks(test.id)
                .enqueue(object : Callback<MutableList<TaskModel>> {
                    override fun onResponse(
                        call: Call<MutableList<TaskModel>>,
                        response: Response<MutableList<TaskModel>>
                    ) {
                        test.tasks = response.body()!!

                        for (task in test.tasks)
                            RetrofitInstance.apiMethods.getVariants(task.id)
                                .enqueue(object : Callback<MutableList<VariantModel>> {
                                    override fun onResponse(
                                        call: Call<MutableList<VariantModel>>,
                                        response: Response<MutableList<VariantModel>>
                                    ) {
                                        task.variantsModel = response.body()!!

                                        UserData.targetTest = test

                                        context.startActivity(
                                            Intent(
                                                context,
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
                                            context,
                                            context.getString(R.string.stringError),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                    }

                    override fun onFailure(call: Call<MutableList<TaskModel>>, t: Throwable) {
                        println(t.localizedMessage)

                        Toast.makeText(
                            context,
                            context.getString(R.string.stringError),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}