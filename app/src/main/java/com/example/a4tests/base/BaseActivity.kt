package com.example.a4tests.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun startActivity(activityClass: Class<*>) {
        startActivity(
            Intent(
                applicationContext,
                activityClass
            )
        )
    }

    fun startActivityWithFinish(activityClass: Class<*>) {
        startActivity(
            Intent(
                applicationContext,
                activityClass
            )
        )

        finish()
    }
}