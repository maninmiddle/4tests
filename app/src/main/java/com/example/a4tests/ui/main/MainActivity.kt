package com.example.a4tests.ui.main

import android.os.Bundle
import com.example.a4tests.animator.ViewAnimator
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.databinding.ActivityMainBinding
import com.example.a4tests.ui.tests_create.TestsCreateActivity
import com.example.a4tests.ui.tests_list.TestsListActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(0, 0)

        val bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        ViewAnimator(bind.mainFrame).fadeIn(1000)
        ViewAnimator(bind.mainFrame).translationY(1000, -100f, 0f)

        bind.mainJoinTest.setOnClickListener {
            startActivity(TestsListActivity::class.java)
        }

        bind.mainJoinCreate.setOnClickListener {
            startActivity(TestsCreateActivity::class.java)
        }

        bind.mainSettings.setOnClickListener { }
        bind.mainAbout.setOnClickListener { }
    }
}