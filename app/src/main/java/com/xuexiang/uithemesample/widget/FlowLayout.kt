package com.xuexiang.uithemesample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 *
 * 自定义ViewGroup：流布局
 *
 * 主要重写：onMeasure、onLayout这两个方法
 *
 * @author xuexiang
 * @since 2023/2/15 23:32
 */
class FlowLayout : ViewGroup {

    private val mAllViews = mutableListOf<MutableList<View>>()
    private val mLineHeight = mutableListOf<Int>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        var realWidth = 0
        var realHeight = 0
        var lineWidth = 0
        var lineHeight = 0
        val cCount = childCount
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = (child.measuredWidth + lp.leftMargin + lp.rightMargin)
            val childHeight = (child.measuredHeight + lp.topMargin + lp.bottomMargin)
            if (lineWidth + childWidth > sizeWidth - paddingLeft - paddingRight) {
                realWidth = Math.max(realWidth, lineWidth)
                lineWidth = childWidth
                realHeight += lineHeight
                lineHeight = childHeight
            } else {
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }
            if (i == cCount - 1) {
                realWidth = Math.max(lineWidth, realWidth)
                realHeight += lineHeight
            }
        }
        setMeasuredDimension(
            if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else realWidth,
            if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else realHeight
        )
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()
        mLineHeight.clear()
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        var lineViews = mutableListOf<View>()
        val cCount = childCount
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            val realChildWidth = childWidth + lp.leftMargin + lp.rightMargin
            val realChildHeight = childHeight + lp.topMargin + lp.bottomMargin
            if (lineWidth + realChildWidth > width - paddingLeft - paddingRight) {
                mLineHeight.add(lineHeight)
                mAllViews.add(lineViews)
                lineWidth = 0
                lineHeight = realChildHeight
                lineViews = mutableListOf()
            }
            lineWidth += realChildWidth
            lineHeight = Math.max(lineHeight, realChildHeight)
            lineViews.add(child)
        }
        mLineHeight.add(lineHeight)
        mAllViews.add(lineViews)
        var left = paddingStart
        var top = paddingTop
        val lineNum = mAllViews.size
        for (i in 0 until lineNum) {
            lineViews = mAllViews[i]
            lineHeight = mLineHeight[i]
            for (j in lineViews.indices) {
                val child = lineViews[j]
                if (child.visibility == GONE) {
                    continue
                }
                val lp = child.layoutParams as MarginLayoutParams
                if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    val end = width - (left + lp.marginStart)
                    val tc = top + lp.topMargin
                    val start = end - child.measuredWidth
                    val bc = tc + child.measuredHeight
                    child.layout(start, tc, end, bc)
                } else {
                    val lc = left + lp.marginStart
                    val tc = top + lp.topMargin
                    val rc = lc + child.measuredWidth
                    val bc = tc + child.measuredHeight
                    child.layout(lc, tc, rc, bc)
                }
                left += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }
            left = paddingStart
            top += lineHeight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}