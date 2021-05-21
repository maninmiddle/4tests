package com.example.a4tests.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tests.R
import com.example.a4tests.data.UserData
import com.example.a4tests.databinding.VariantsCreateItemBinding
import java.util.*

class VariantsCreateAdapter(
    private val context: Context,
    private val variants: MutableList<String>,
    private val taskIdx: Int
) : RecyclerView.Adapter<VariantsCreateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.variants_create_item,
                    parent,
                    false
                )
        )

    override fun getItemCount() = variants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bind = VariantsCreateItemBinding.bind(holder.itemView)

        bind.variantsCreateItemText.setText("")
        bind.variantsCreateItemCheckbox.isChecked = false

        bind.variantsCreateItemText.hint =
            String.format(
                Locale.getDefault(),
                "%s %d",
                context.getString(R.string.stringVariant),
                position + 1
            )

        bind.variantsCreateItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                UserData.targetTest.tasks[taskIdx].rightVariants.add(position)
            else
                UserData.targetTest.tasks[taskIdx].rightVariants.remove(position)
        }

        bind.variantsCreateItemText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                UserData.targetTest.tasks[taskIdx].variants[position] = s.toString()
            }
        })

        bind.variantsCreateItemText.setHintTextColor(context.getColor(R.color.colorWhite))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}