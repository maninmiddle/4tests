package com.example.a4tests.animator

import android.view.View
import com.github.florent37.viewanimator.ViewAnimator

class ViewAnimator(
    private val view: View
) {

    fun fadeIn(duration: Long) {
        ViewAnimator
            .animate(view)
            .fadeIn()
            .duration(duration)
            .start()
    }

    fun fadeOut(duration: Long) {
        ViewAnimator
            .animate(view)
            .fadeIn()
            .duration(duration)
            .start()
    }

    fun translationX(
        duration: Long,
        translationStart: Float,
        translationEnd: Float
    ) {
        ViewAnimator
            .animate(view)
            .translationX(translationStart, translationEnd)
            .duration(duration)
            .start()
    }

    fun translationY(
        duration: Long,
        translationStart: Float,
        translationEnd: Float
    ) {
        ViewAnimator
            .animate(view)
            .translationY(translationStart, translationEnd)
            .duration(duration)
            .start()
    }
}