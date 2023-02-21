/*
 * Copyright (C) 2023 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.uithemesample.widget

import android.animation.ValueAnimator
import kotlin.jvm.JvmOverloads
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.xuexiang.uithemesample.R
import com.xuexiang.xui.utils.DensityUtils

/**
 * 自定义View：用于显示 Loading 的 [View]，支持颜色和大小的设置。
 *
 * 主要重写：onDraw方法
 *
 * @author xuexiang
 * @since 2022/12/11 16:40
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.LoadingStyle
) : View(context, attrs, defStyleAttr) {

    private var mSize = 0
    private var mPaintColor = 0
    private var mAnimateValue = 0
    private var mAnimator: ValueAnimator? = null
    private var mPaint: Paint? = null

    init {
        initAttrs(context, attrs, defStyleAttr)
        initPaint()
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0)
        mSize = array.getDimensionPixelSize(
            R.styleable.LoadingView_lv_loading_view_size,
            DensityUtils.dp2px(context, DEFAULT_LOADING_VIEW_SIZE)
        )
        mPaintColor = array.getColor(R.styleable.LoadingView_lv_loading_view_color, Color.WHITE)
        array.recycle()
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            color = mPaintColor
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }
    }

    fun setColor(color: Int) {
        mPaintColor = color
        mPaint?.color = color
        invalidate()
    }

    fun setSize(size: Int) {
        mSize = size
        requestLayout()
    }

    private val mUpdateListener = AnimatorUpdateListener { animation ->
        mAnimateValue = animation.animatedValue as Int
        invalidate()
    }

    fun start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1).apply {
                addUpdateListener(mUpdateListener)
                duration = 600
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        } else if (!mAnimator!!.isStarted) {
            mAnimator?.start()
        }
    }

    fun stop() {
        mAnimator?.run {
            removeUpdateListener(mUpdateListener)
            removeAllUpdateListeners()
            cancel()
        }
        mAnimator = null
    }

    private fun drawLoading(canvas: Canvas, rotateDegrees: Int) {
        val width = mSize / 12
        val height = mSize / 6
        mPaint?.strokeWidth = width.toFloat()
        canvas.rotate(rotateDegrees.toFloat(), mSize / 2f, mSize / 2f)
        canvas.translate(mSize / 2f, mSize / 2f)
        for (i in 0 until LINE_COUNT) {
            canvas.rotate(DEGREE_PER_LINE.toFloat())
            mPaint?.run {
                alpha = (255f * (i + 1) / LINE_COUNT).toInt()
                canvas.translate(0f, -mSize / 2f + width / 2f)
                canvas.drawLine(0f, 0f, 0f, height.toFloat(), this)
                canvas.translate(0f, mSize / 2f - width / 2f)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mSize, mSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val saveCount =
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE)
        canvas.restoreToCount(saveCount)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            start()
        } else {
            stop()
        }
    }

    companion object {
        private const val LINE_COUNT = 12
        private const val DEGREE_PER_LINE = 360 / LINE_COUNT
        private const val DEFAULT_LOADING_VIEW_SIZE = 32F
    }
}