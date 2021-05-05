package com.example.a4tests.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tests.R
import com.example.a4tests.animator.ViewAnimator
import com.example.a4tests.databinding.TestsListItemBinding
import com.example.a4tests.model.TestModel
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
        bind.testEndDate.text =
            String.format(
                Locale.getDefault(),
                "До %s",
                test.endDate
            )

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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}