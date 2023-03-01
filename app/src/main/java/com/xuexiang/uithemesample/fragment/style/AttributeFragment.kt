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

package com.xuexiang.uithemesample.fragment.style

import com.xuexiang.uithemesample.R
import com.xuexiang.uithemesample.core.BaseSimpleListFragment
import com.xuexiang.uithemesample.utils.XToastUtils
import com.xuexiang.uithemesample.utils.resolveAttributeToDimension
import com.xuexiang.uithemesample.utils.resolveDimension
import com.xuexiang.xpage.annotation.Page

/**
 * Attribute的获取:
 *
 * 1.属性集的获取: 使用`context.obtainStyledAttributes`进行整体获取。
 * 2.单个属性的获取: 使用`context.theme.resolveAttribute`进行获取。
 *
 * @author xuexiang
 * @since 2023/3/2 02:31
 */
@Page(name = "Attribute的获取")
class AttributeFragment : BaseSimpleListFragment() {

    override fun initSimpleData(lists: MutableList<String>): MutableList<String> {
        return lists.apply {
            add("使用`context.theme.resolveAttribute`进行获取")
            add("使用`context.obtainStyledAttributes`进行获取")
        }
    }

    override fun onItemClick(position: Int) {
        when(position) {
            0 -> {
                val start = System.nanoTime()
                val value = context?.theme?.resolveAttributeToDimension(R.attr.test_dimension)
                val time = System.nanoTime() - start
                XToastUtils.toast("value:$value, cost:$time nanos")
            }
            1 -> {
                val start = System.nanoTime()
                val value = context?.resolveDimension(R.attr.test_dimension)
                val time = System.nanoTime() - start
                XToastUtils.toast("value:$value, cost:$time nanos")
            }
        }
    }


}