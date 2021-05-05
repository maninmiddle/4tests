package com.example.a4tests.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.a4tests.base.BaseActivity
import com.example.a4tests.databinding.ActivitySplashBinding
import com.example.a4tests.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivityWithFinish(MainActivity::class.java)
        }, 2000)
    }
}