package com.kopyl.blockpc.utils

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_add_workstation.*

fun circularAnimation(view: View, endRadius: Float): Animator? {
    val cx = view.width / 2
    val cy = view.height / 2
    val radius: Float = view.width.toFloat()
    return ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, endRadius)
}