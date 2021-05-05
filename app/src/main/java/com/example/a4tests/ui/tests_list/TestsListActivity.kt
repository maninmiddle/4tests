package com.example.a4tests.ui.tests_list

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.a4tests.R
import com.example.a4tests.adapter.TestsListAdapter
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.databinding.ActivityTestsListBinding
import com.example.a4tests.instances.RetrofitInstance
import com.example.a4tests.model.TestModel
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