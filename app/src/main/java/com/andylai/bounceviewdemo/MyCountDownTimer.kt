package com.andylai.bounceviewdemo

import android.os.CountDownTimer

open class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
    }

    override fun onFinish() {
    }
}