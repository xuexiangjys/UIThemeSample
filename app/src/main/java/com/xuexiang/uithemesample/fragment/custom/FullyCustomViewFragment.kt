/*
 * Copyright (C) 2022 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.uithemesample.fragment.custom

import com.xuexiang.uithemesample.core.BaseContainerFragment
import com.xuexiang.uithemesample.fragment.custom.custom.CustomViewFragment
import com.xuexiang.uithemesample.fragment.custom.custom.CustomViewGroupFragment
import com.xuexiang.xpage.annotation.Page

/**
 * 完全自定义View实现自定义控件
 *
 * 自定义View的最基本的方法是:
 *
 * onMeasure()：测量，决定View的大小；
 * onLayout()：布局，决定View在ViewGroup中的位置
 * onDraw():绘制，决定绘制这个View;
 *
 * @author xuexiang
 * @since 2022/7/7 1:18 上午
 */
@Page(name = "完全自定义控件")
class FullyCustomViewFragment : BaseContainerFragment() {

    override fun getPagesClasses(): Array<Class<*>> {
        return arrayOf( //此处填写fragment
            CustomViewFragment::class.java,
            CustomViewGroupFragment::class.java
        )
    }

}