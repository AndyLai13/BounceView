package com.andylai.bounceviewdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.andylai.bounceviewdemo.MyTimer.OnTimerListener

class MainActivity : AppCompatActivity() {

    private lateinit var bounceView: BounceView
    private lateinit var buttonPanel: ConstraintLayout
    private lateinit var buttonActivate: Button
    private lateinit var buttonMinute1: Button
    private lateinit var buttonMinute5: Button
    private lateinit var buttonMinute10: Button

    var screenSaverTimer = MyTimer(5 * 1000, 1000, TimerListener())

    inner class TimerListener : OnTimerListener {

        override fun onFinish() {
            refreshState(State.Reveal)
        }

        override fun onTick(millisUntilFinished: Long) {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bounceView = findViewById(R.id.bounce_view)
        buttonPanel = findViewById(R.id.button_panel)
        buttonActivate = findViewById(R.id.activate)
        buttonMinute1 = findViewById(R.id.minute_1)
        buttonMinute5 = findViewById(R.id.minute_5)
        buttonMinute10 = findViewById(R.id.minute_10)

        buttonActivate.setOnClickListener { refreshState(State.Reveal) }
        buttonMinute1.setOnClickListener(mOnClickListener)
        buttonMinute5.setOnClickListener(mOnClickListener)
        buttonMinute10.setOnClickListener(mOnClickListener)

        bounceView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    refreshState(State.Hide)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private val mOnClickListener = View.OnClickListener {
        screenSaverTimer.cancel()
        screenSaverTimer = when (it) {
            buttonMinute1 -> MyTimer(1 * 60* 1000, 1000, TimerListener())
            buttonMinute5 -> MyTimer(5 * 60 * 1000, 1000, TimerListener())
            buttonMinute10 -> MyTimer(10 * 60 * 1000, 1000, TimerListener())
            else -> MyTimer(5 * 1000, 1000, TimerListener())
        }
        screenSaverTimer.start()
    }

    sealed class State {
        object Hide : State()
        object Reveal : State()
    }

    private fun refreshState(curState: State) {
        when (curState) {
            is State.Hide -> {
                buttonPanel.bringToFront()
                bounceView.visibility = View.GONE
                buttonPanel.visibility = View.VISIBLE
            }
            is State.Reveal -> {
                bounceView.bringToFront()
                bounceView.visibility = View.VISIBLE
                buttonPanel.visibility = View.GONE
            }
        }
    }
}
