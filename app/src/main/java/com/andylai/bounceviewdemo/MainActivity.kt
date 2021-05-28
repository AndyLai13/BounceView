package com.andylai.bounceviewdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.andylai.bounceviewdemo.TimeFormatter.MILLIS_PER_SECOND
import com.andylai.bounceviewdemo.TimeFormatter.SECOND_PER_MINUTE

class MainActivity : AppCompatActivity() {

    private lateinit var bounceView: BounceView
    private lateinit var buttonPanel: ConstraintLayout

    private var screenSaverTimer: CountDownTimer? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bounceView = findViewById<BounceView>(R.id.bounce_view)
            .apply {
                setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            refreshState(State.Hide)
                            true
                        }
                        else -> false
                    }
                }
            }
        buttonPanel = findViewById(R.id.button_panel)
        findViewById<View>(R.id.activate).setOnClickListener { refreshState(State.Reveal) }
        findViewById<View>(R.id.minute_1).setOnClickListener { startTimer(1) }
        findViewById<View>(R.id.minute_5).setOnClickListener { startTimer(5) }
        findViewById<View>(R.id.minute_10).setOnClickListener { startTimer(10) }
    }

    private fun startTimer(minute: Long) {
        screenSaverTimer?.cancel()
        screenSaverTimer = object : MyCountDownTimer(
            minute * SECOND_PER_MINUTE * MILLIS_PER_SECOND,
            MILLIS_PER_SECOND
        ) {
            override fun onFinish() {
                refreshState(State.Reveal)
            }
        }
        screenSaverTimer?.start()
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
