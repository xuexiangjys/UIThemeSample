/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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
package com.xuexiang.uithemesample.fragment

import com.xuexiang.uithemesample.core.BaseContainerFragment
import com.xuexiang.uithemesample.fragment.custom.CombineViewFragment
import com.xuexiang.uithemesample.fragment.custom.ExtendViewFragment
import com.xuexiang.uithemesample.fragment.custom.FullyCustomViewFragment
import com.xuexiang.xpage.annotation.Page

/**
 * 这个只是一个空壳Fragment，只是用于演示而已
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "自定义控件")
class CustomViewFragment : BaseContainerFragment() {

    override fun getPagesClasses(): Array<Class<*>> {
        return arrayOf( //此处填写fragment
            ExtendViewFragment::class.java,
            CombineViewFragment::class.java,
            FullyCustomViewFragment::class.java
        )
    }

}