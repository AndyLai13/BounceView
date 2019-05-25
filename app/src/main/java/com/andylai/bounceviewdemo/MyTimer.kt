package com.andylai.bounceviewdemo

import android.os.CountDownTimer


// CountDownTimer is an abstract class that has to be initiated.
// unit: millisecond for millisInFuture
class MyTimer(millisInFuture: Long, countDownInterval: Long, private val mListener: OnTimerListener) :
    CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        mListener.onTick(millisUntilFinished)
    }

    override fun onFinish() {
        mListener.onFinish()
    }

    interface OnTimerListener {

        fun onTick(millisUntilFinished: Long)

        fun onFinish()
    }
}