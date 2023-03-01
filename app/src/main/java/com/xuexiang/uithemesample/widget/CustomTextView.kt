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

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.xuexiang.uithemesample.R
import com.xuexiang.xui.widget.edittext.AsteriskPasswordTransformationMethod
import com.xuexiang.xutil.display.Colors

/**
 * 自定义TextView
 *
 * @author xuexiang
 * @since 2023/2/22 01:09
 */
@SuppressLint("AppCompatCustomView")
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.CustomTextStyle,
    defStyleRes: Int = R.style.DefaultCustomTextStyle
) : TextView(context, attrs, defStyleAttr, defStyleRes) {

    var size = MEDIUM
        set(value) {
            if (field != value) {
                field = value
                textSize = when (value) {
                    SMALL -> 12F
                    MEDIUM -> 16F
                    LARGE -> 21F
                    else -> 16F
                }
                setTextColor(
                    when (value) {
                        SMALL -> Colors.GRAY
                        MEDIUM -> Colors.BLACK
                        LARGE -> Colors.BLUE
                        else -> Colors.BLACK
                    }
                )
            }
        }
    var isPassword = false
        set(value) {
            if (field != value) {
                field = value
                transformationMethod =
                    if (value) AsteriskPasswordTransformationMethod.getInstance() else null
            }
        }

    init {
        val typedValue = TypedValue()
        val isSuccess = context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        if (isSuccess) {
            typedValue.getDimension(context.resources.displayMetrics)
        }

        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            defStyleAttr,
            defStyleRes
        )
        size = array.getInteger(R.styleable.CustomTextView_ctv_size, size)
        isPassword = array.getBoolean(R.styleable.CustomTextView_ctv_is_password, isPassword)
        array.recycle()
    }


    companion object {
        const val SMALL = 0
        const val MEDIUM = 1
        const val LARGE = 2
    }

}