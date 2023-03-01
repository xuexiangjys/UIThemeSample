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

package com.xuexiang.uithemesample.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes

/**
 * Theme相关拓展方法
 *
 * @author xuexiang
 * @since 2023/3/2 02:11
 */


/**
 *
 * 使用`context.theme.resolveAttribute`进行获取
 *
 */
fun Resources.Theme.resolveAttributeToDimension(@AttrRes attributeId: Int, defaultValue: Float = 0F) : Float {
    val typedValue = TypedValue()
    return if (resolveAttribute(attributeId, typedValue, true)) {
        typedValue.getDimension(resources.displayMetrics)
    } else {
        defaultValue
    }
}

/**
 * 使用`context.obtainStyledAttributes`进行获取
 */
fun Context.resolveDimension(@AttrRes attributeId: Int, defaultValue: Float = 0F) : Float {
    val typedArray = obtainStyledAttributes(intArrayOf(attributeId))
    return try {
        typedArray.getDimension(0, defaultValue)
    } finally {
        typedArray.recycle()
    }
}

