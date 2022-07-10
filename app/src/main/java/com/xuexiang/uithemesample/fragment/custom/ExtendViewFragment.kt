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

import android.view.LayoutInflater
import android.view.ViewGroup
import com.xuexiang.uithemesample.core.ViewBindingFragment
import com.xuexiang.uithemesample.databinding.FragmentCombineViewBinding
import com.xuexiang.uithemesample.databinding.FragmentExtendViewBinding
import com.xuexiang.xpage.annotation.Page

/**
 * 继承组件的方式实现自定义控件
 *
 * @author xuexiang
 * @since 2022/7/7 1:18 上午
 */
@Page(name = "继承控件")
class ExtendViewFragment : ViewBindingFragment<FragmentExtendViewBinding>() {

    override fun viewBindingInflate(
        inflater: LayoutInflater,
        container: ViewGroup
    ) = FragmentExtendViewBinding.inflate(inflater, container, false)

    override fun initViews() {

    }

}