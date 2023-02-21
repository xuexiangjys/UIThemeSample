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
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.xuexiang.uithemesample.R
import com.xuexiang.xui.utils.DensityUtils
import com.xuexiang.xui.utils.ResUtils
import com.xuexiang.xui.utils.ThemeUtils
import kotlin.math.roundToLong

/**
 * 多行计数输入框【组合控件的方式】
 * ignoreCnOrEn 为false的时候
 * 1个中文算1个
 * 2个英文算1个
 * 另外：如：只有一个英文时也算1个
 *
 * 主要需要使用LayoutInflater去加载组合控件的布局。
 *
 * @author xuexiang
 * @since 2023/2/22 01:08
 */
class MultiEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.MultiEditTextStyle
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var editText: EditText
    private lateinit var countTextView: TextView

    /**
     * 最大输入的字符数
     */
    private var mMaxCount = 0

    /**
     * 输入提示文字
     */
    private var mHintText: String? = null

    /**
     * 提示文字的颜色
     */
    private var mHintTextColor = 0

    /**
     * 是否忽略中英文差异
     */
    private var mIgnoreCnOrEn = false

    /**
     * 输入内容
     */
    private var mContentText: String? = null

    /**
     * 输入框文字大小
     */
    private var mContentTextSize = 0

    /**
     * 输入框文字颜色
     */
    private var mContentTextColor = 0

    /**
     * 输入框高度
     */
    private var mContentViewHeight = 0f

    /**
     * 输入框高度是否是固定高度，默认是true
     */
    private var mIsFixHeight = false

    /**
     * 输入框padding
     */
    private var mContentPadding = 0

    /**
     * 输入框背景
     */
    private var mContentBackground: Drawable? = null

    /**
     * 是否显示剩余数目
     */
    private var mIsShowSurplusNumber = false

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MultiEditText, defStyleAttr, 0)
        mMaxCount = typedArray.getInteger(R.styleable.MultiEditText_met_maxCount, 240)
        mIgnoreCnOrEn = typedArray.getBoolean(R.styleable.MultiEditText_met_ignoreCnOrEn, true)
        mHintText = typedArray.getString(R.styleable.MultiEditText_met_hintText)
        mHintTextColor = typedArray.getColor(
            R.styleable.MultiEditText_met_hintTextColor,
            ThemeUtils.resolveColor(getContext(), R.attr.xui_config_color_hint_text)
        )
        mContentPadding = typedArray.getDimensionPixelSize(
            R.styleable.MultiEditText_met_contentPadding,
            DensityUtils.dp2px(context, 10f)
        )
        mContentBackground = ResUtils.getDrawableAttrRes(
            getContext(),
            typedArray,
            R.styleable.MultiEditText_met_contentBackground
        )
        mContentText = typedArray.getString(R.styleable.MultiEditText_met_contentText)
        mContentTextColor = typedArray.getColor(
            R.styleable.MultiEditText_met_contentTextColor,
            ThemeUtils.resolveColor(getContext(), R.attr.xui_config_color_input_text)
        )
        mContentTextSize = typedArray.getDimensionPixelSize(
            R.styleable.MultiEditText_met_contentTextSize,
            DensityUtils.sp2px(context, 14f)
        )
        mContentViewHeight = typedArray.getDimensionPixelSize(
            R.styleable.MultiEditText_met_contentViewHeight,
            DensityUtils.dp2px(context, 140f)
        ).toFloat()
        mIsFixHeight = typedArray.getBoolean(R.styleable.MultiEditText_met_isFixHeight, true)
        mIsShowSurplusNumber =
            typedArray.getBoolean(R.styleable.MultiEditText_met_showSurplusNumber, false)
        typedArray.recycle()
    }

    private fun initView() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_multiline_edittext, this)
        editText = view.findViewById(R.id.met_input)
        countTextView = view.findViewById(R.id.met_number)
        if (background == null) {
            setBackgroundResource(R.drawable.mlet_selector_bg)
        }
        editText.run {
            addTextChangedListener(mTextWatcher)
            hint = mHintText
            setHintTextColor(mHintTextColor)
            setText(mContentText)
            setPadding(mContentPadding, mContentPadding, mContentPadding, 0)
            if (mContentBackground != null) {
                background = mContentBackground
            }
            setTextColor(mContentTextColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentTextSize.toFloat())
            if (mIsFixHeight) {
                height = mContentViewHeight.toInt()
            } else {
                minHeight = mContentViewHeight.toInt()
            }
        }

        /**
         * 配合 mTvInputNumber xml的 android:focusable="true"
         * android:focusableInTouchMode="true"
         * 在mlet_input设置完文本后
         * 不给mlet_input 焦点
         */
        countTextView.requestFocus()
        //init
        configCount()
        editText.setSelection(editText.length()) // 将光标移动最后一个字符后面
        /**
         * focus后给背景设置Selected
         */
        editText.onFocusChangeListener = OnFocusChangeListener { _, b ->
            this@MultiEditText.isSelected = b
        }
    }

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            var mEditStart = editText.selectionStart
            var mEditEnd = editText.selectionEnd

            // 先去掉监听器，否则会出现栈溢出
            editText.removeTextChangedListener(this)
            if (mIgnoreCnOrEn) {
                //当输入字符个数超过限制的大小时，进行截断操作
                while (calculateLengthIgnoreCnOrEn(editable.toString()) > mMaxCount) {
                    editable.delete(mEditStart - 1, mEditEnd)
                    mEditStart--
                    mEditEnd--
                }
            } else {
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(editable.toString()) > mMaxCount) { // 当输入字符个数超过限制的大小时，进行截断操作
                    editable.delete(mEditStart - 1, mEditEnd)
                    mEditStart--
                    mEditEnd--
                }
            }
            editText.setSelection(mEditStart)
            // 恢复监听器
            editText.addTextChangedListener(this)
            //update
            configCount()
        }
    }

    init {
        initAttrs(context, attrs, defStyleAttr)
        initView()
    }

    private fun calculateLength(c: CharSequence): Long {
        var len = 0.0
        for (element in c) {
            val tmp = element.code
            if (tmp in 1..126) {
                len += 0.5
            } else {
                len++
            }
        }
        return len.roundToLong()
    }

    private fun calculateLengthIgnoreCnOrEn(c: CharSequence?): Int {
        return c?.length ?: 0
    }

    private fun configCount() {
        if (mIgnoreCnOrEn) {
            val nowCount = calculateLengthIgnoreCnOrEn(editText.text.toString())
            updateCount(nowCount)
        } else {
            val nowCount = calculateLength(editText.text.toString()).toInt()
            updateCount(nowCount)
        }
    }

    private fun updateCount(nowCount: Int) {
        if (mIsShowSurplusNumber) {
            countTextView.text = (mMaxCount - nowCount).toString() + "/" + mMaxCount
        } else {
            countTextView.text = "$nowCount/$mMaxCount"
        }
    }

    private fun calculateContentLength(content: String): Long {
        return if (mIgnoreCnOrEn) calculateLengthIgnoreCnOrEn(content).toLong() else calculateLength(
            content
        )
    }

    private fun getSubStringIndex(content: String): Int {
        if (!mIgnoreCnOrEn) {
            var len = 0.0
            for (i in content.indices) {
                val tmp = content[i].code
                if (tmp in 1..126) {
                    len += 0.5
                } else {
                    len++
                }
                if (len.roundToLong() == mMaxCount.toLong()) {
                    return i + 1
                }
            }
        }
        return mMaxCount
    }
    /**
     * 获取输入的内容
     *
     * @return
     */
    /**
     * 设置填充内容
     *
     * @param content
     */
    var contentText: String?
        get() {
            mContentText = editText.text?.toString() ?: ""
            return mContentText
        }
        set(value) {
            var content = value
            if (content != null && calculateContentLength(content) > mMaxCount) {
                content = content.substring(0, getSubStringIndex(content))
            }
            mContentText = content
            editText.setText(mContentText)
        }

    fun setContentTextSize(size: Int) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
    }

    fun setContentTextColor(color: Int) {
        editText.setTextColor(color)
    }

    fun setHintColor(color: Int) {
        editText.setHintTextColor(color)
    }

    var hintText: String?
        get() {
            mHintText = editText.hint?.toString() ?: ""
            return mHintText
        }
        set(hintText) {
            mHintText = hintText
            editText.hint = hintText
        }

    fun setMaxCount(max_count: Int): MultiEditText {
        mMaxCount = max_count
        configCount()
        return this
    }

    fun setIgnoreCnOrEn(ignoreCnOrEn: Boolean): MultiEditText {
        mIgnoreCnOrEn = ignoreCnOrEn
        configCount()
        return this
    }

    fun setIsShowSurplusNumber(isShowSurplusNumber: Boolean): MultiEditText {
        mIsShowSurplusNumber = isShowSurplusNumber
        configCount()
        return this
    }

    /**
     * 输入的内容是否为空
     *
     * @return
     */
    val isEmpty: Boolean
        get() = TextUtils.isEmpty(contentText)

    /**
     * 输入的内容是否不为空
     *
     * @return
     */
    val isNotEmpty: Boolean
        get() = !TextUtils.isEmpty(contentText)
}