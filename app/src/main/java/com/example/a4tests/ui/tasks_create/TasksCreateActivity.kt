package com.example.a4tests.ui.tasks_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4tests.R
import com.example.a4tests.adapter.TasksCreateAdapter
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.ActivityTasksCreateBinding
import com.example.a4tests.model.TaskModel
import com.example.a4tests.ui.tests_confirm.TestsConfirmActivity

@SuppressLint("ClickableViewAccessibility")
class TasksCreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = ActivityTasksCreateBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.tasksCreateRecycler.setOnTouchListener { _, _ -> false }

        bind.tasksCreateRecycler.layoutManager =
            LinearLayoutManager(this)

        bind.tasksCreateRecycler.adapter =
            TasksCreateAdapter(this, UserData.targetTest.tasks)

        bind.tasksCreateAddTask.setOnClickListener {
            if (UserData.targetTest.tasks.size >= 50)
                Toast.makeText(
                    this,
                    getString(R.string.stringTasksLimit),
                    Toast.LENGTH_SHORT
                ).show()
            else {
                if (currentFocus != null)
                    currentFocus!!.clearFocus()

                UserData.targetTest.tasks.add(
                    TaskModel("", mutableListOf("", ""), mutableListOf())
                )

                bind.tasksCreateRecycler.adapter!!.notifyItemInserted(UserData.targetTest.tasks.size - 1)
            }
        }

        bind.tasksCreateContinue.setOnClickListener {
            var canContinue = true

            for (task in UserData.targetTest.tasks) {
                if (!canContinue)
                    break

                if (task.text.isEmpty()) {
                    Toast.makeText(
                        this,
                        getString(R.string.stringTaskNoText),
                        Toast.LENGTH_SHORT
                    ).show()

                    canContinue = false
                }

                for (variant in task.variants) {
                    if (variant.isEmpty() && canContinue) {
                        Toast.makeText(
                            this,
                            getString(R.string.stringVariantNoText),
                            Toast.LENGTH_SHORT
                        ).show()

                        canContinue = false
                    }
                }

                if (task.rightVariants.isEmpty() && canContinue) {
                    Toast.makeText(
                        this,
                        getString(R.string.stringTaskNoRightVariant),
                        Toast.LENGTH_SHORT
                    ).show()

                    canContinue = false
                }
            }

            if (canContinue)
                startActivity(TestsConfirmActivity::class.java)
        }

        bind.tasksCreateBack.setOnClickListener {
            finish()
        }
    }
}