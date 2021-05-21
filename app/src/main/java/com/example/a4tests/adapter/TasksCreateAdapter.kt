package com.example.a4tests.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tests.R
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.TasksCreateItemBinding
import com.example.a4tests.model.TaskModel
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class TasksCreateAdapter(
    private val context: Context,
    private val tasks: MutableList<TaskModel>
) : RecyclerView.Adapter<TasksCreateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.tasks_create_item,
                    parent,
                    false
                )
        )

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bind = TasksCreateItemBinding.bind(holder.itemView)

        bind.tasksCreateItemText.hint =
            String.format(
                Locale.getDefault(),
                "%s %d",
                context.getString(R.string.stringTask),
                position + 1
            )

        bind.tasksCreateItemText.setHintTextColor(context.getColor(R.color.colorWhite))

        bind.tasksCreateItemRecycler.setOnTouchListener { _, _ -> false }

        bind.tasksCreateItemRecycler.layoutManager =
            LinearLayoutManager(context)

        bind.tasksCreateItemRecycler.adapter =
            VariantsCreateAdapter(context, UserData.targetTest.tasks[position].variants, position)

        bind.tasksCreateItemText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                UserData.targetTest.tasks[position].text = s.toString()
            }
        })

        bind.tasksCreateItemAddVariant.setOnClickListener {
            if (UserData.targetTest.tasks[position].variants.size >= 10)
                Toast.makeText(
                    context,
                    context.getString(R.string.stringVariantsLimit),
                    Toast.LENGTH_SHORT
                ).show()
            else {
                UserData.targetTest.tasks[position].variants.add("")

                bind.tasksCreateItemDeleteVariant.visibility = View.VISIBLE

                bind.tasksCreateItemRecycler.adapter!!.notifyItemInserted(UserData.targetTest.tasks[position].variants.size - 1)
            }
        }

        bind.tasksCreateItemDeleteVariant.setOnClickListener {
            UserData.targetTest.tasks[position].variants.removeAt(UserData.targetTest.tasks[position].variants.size - 1)

            if (UserData.targetTest.tasks[position].variants.size == 2)
                bind.tasksCreateItemDeleteVariant.visibility = View.GONE

            bind.tasksCreateItemRecycler.adapter!!.notifyItemRemoved(UserData.targetTest.tasks[position].variants.size)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}