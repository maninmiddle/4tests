package com.example.a4tests.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tests.R
import com.example.a4tests.databinding.TestsSolveButtonBinding
import com.example.a4tests.model.VariantModel
import com.example.a4tests.ui.tests_solve.TestsSolveActivity

class TestsSolveAdapter(
    private val context: Context,
    private val variants: MutableList<VariantModel>
) : RecyclerView.Adapter<TestsSolveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.tests_solve_button,
                    parent,
                    false
                )
        )

    override fun getItemCount() = variants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bind = TestsSolveButtonBinding.bind(holder.itemView)
        val context = (context as TestsSolveActivity)
        val variant = variants[position]

        bind.testSolveButton.text = variant.text
        bind.testSolveButton.setOnClickListener {
            if (variant.isRight == 1)
                context.answer(true)
            else
                context.answer(false)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}