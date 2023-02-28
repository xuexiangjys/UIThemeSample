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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.xuexiang.xui.utils.DensityUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义SurfaceView
 *
 * @author xuexiang
 * @since 2023/2/28 01:34
 */
class CustomSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var mSurfaceHolder: SurfaceHolder = holder
    private lateinit var mRenderThread: RenderThread
    private var mIsDrawing = false
    private var mBgColor: Int = Color.RED
    private val mIndex = AtomicInteger(0)

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private var mColors =
        intArrayOf(Color.RED, Color.BLACK, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.YELLOW)

    init {
        mSurfaceHolder.addCallback(this)
        mTextPaint.run {
            color = Color.WHITE
            textSize = DensityUtils.sp2px(context, 24F).toFloat()
            textAlign = Paint.Align.CENTER
        }
        mBgColor = refreshColor()
    }

    fun start() {
        if (mIsDrawing) {
            return
        }
        mIsDrawing = true
        mRenderThread = RenderThread()
        mRenderThread.start()
    }

    fun stop() {
        if (!mIsDrawing) {
            return
        }
        mIsDrawing = false
        mRenderThread.interrupt()
    }

    //==================绘制========================//

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {
        start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    /**
     * 绘制界面的线程
     *
     * @author Administrator
     */
    private inner class RenderThread : Thread() {

        override fun run() {
            // 不停绘制界面
            while (mIsDrawing) {
                drawUI()
                try {
                    sleep(1000)
                } catch (_: InterruptedException) {
                }
            }
        }
    }

    /**
     * 界面绘制
     */
    private fun drawUI() {
        val canvas = mSurfaceHolder.lockCanvas()
        try {
            drawCanvas(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mSurfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawCanvas(canvas: Canvas?) {
        // 在 canvas 上绘制需要的图形
        canvas?.run {
            drawColor(mBgColor)
            drawText(mIndex.toString(), mTextPaint)
            mBgColor = refreshColor()
        }
    }

    private fun Canvas.drawText(text: String, textPaint: Paint) {
        val textRect = Rect()
        textPaint.getTextBounds(text, 0, text.length, textRect)
        drawText(
            text,
            width / 2F,
            height / 2F - (textRect.top + textRect.bottom) / 2F,
            textPaint
        )
    }

    private fun refreshColor() = mColors[mIndex.getAndIncrement() % mColors.size]

}