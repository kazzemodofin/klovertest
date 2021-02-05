package com.kazeem.spinningwheel.presentation.spinner

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.Nullable
import com.kazeem.spinningwheel.R
import com.kazeem.spinningwheel.presentation.spinner.OnLuckyWheelReachTheTarget
import com.kazeem.spinningwheel.presentation.spinner.OnRotationListener
import com.kazeem.spinningwheel.presentation.spinner.SpinnerItem
import com.kazeem.spinningwheel.presentation.spinner.SpinnerView
import kotlin.math.abs

class SpinWheel : FrameLayout, View.OnTouchListener, OnRotationListener {
    private lateinit var spinnerlView: SpinnerView
    private lateinit var arrow: ImageView
    private var target = -1
    private var isRotate = false
    private val SWIPE_DISTANCE_THRESHOLD = 100
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    var dx = 0f
    var dy = 0f

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        initComponent()
        applyAttribute(attrs)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initComponent()
        applyAttribute(attrs)
    }

    private fun initComponent() {
        View.inflate(context, R.layout.spinner_wheel_layout, this)
        setOnTouchListener(this)
        spinnerlView = findViewById(R.id.wv_main_wheel)
        spinnerlView.setOnRotationListener(this)
        arrow = findViewById(R.id.iv_arrow)
    }

    private fun applyAttribute(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinWheel, 0, 0)
        try {
            val backgroundColor = typedArray.getColor(R.styleable.SpinWheel_background_color, Color.GREEN)
            val arrowImage = typedArray.getResourceId(R.styleable.SpinWheel_arrow_image, R.drawable.wheel_ticker)
            spinnerlView.setWheelBackgoundWheel(backgroundColor)
            arrow.setImageResource(arrowImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        typedArray.recycle()
    }

    /**
     * Function to set lucky wheel reach the target listener
     *
     * @param onLuckyWheelReachTheTarget Lucky wheel listener
     */
    fun setLuckyWheelReachTheTarget(onLuckyWheelReachTheTarget: OnLuckyWheelReachTheTarget) {
        spinnerlView.setWheelListener(onLuckyWheelReachTheTarget)
    }

    /**
     * Function to add items to wheel items
     *
     * @param wheelItems Wheel items
     */
    fun addWheelItems(wheelItems: List<SpinnerItem>) {
        spinnerlView.addSpinnerItems(wheelItems)
    }

    /**
     * @param target target to rotate before swipe
     */
    fun setTarget(target: Int) {
        this.target = target
    }

    /**
     * Function to rotate wheel to degree
     *
     * @param number Number to rotate
     */
    fun rotateWheelTo(number: Int) {
        isRotate = true
        spinnerlView.resetRotationLocationToZeroAngle(number)
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (target < 0 || isRotate) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                y2 = event.y
                dx = x2 - x1
                dy = y2 - y1
                if (abs(dx) > abs(dy)) {
                    if (dx < 0 && abs(dx) > SWIPE_DISTANCE_THRESHOLD) rotateWheelTo(
                        target
                    )
                } else {
                    if (dy > 0 && abs(dy) > SWIPE_DISTANCE_THRESHOLD) rotateWheelTo(
                        target
                    )
                }
            }
            else -> return true
        }
        return true
    }

    override fun onFinishRotation() {
        isRotate = false
    }

}