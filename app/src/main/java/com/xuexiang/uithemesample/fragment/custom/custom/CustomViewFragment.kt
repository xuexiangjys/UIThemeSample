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

package com.xuexiang.uithemesample.fragment.custom.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.xuexiang.uithemesample.core.ViewBindingFragment
import com.xuexiang.uithemesample.databinding.FragmentCustomViewBinding
import com.xuexiang.xpage.annotation.Page

/**
 * 自定义View实现自定义控件
 *
 * @author xuexiang
 * @since 2022/7/7 1:18 上午
 */
@Page(name = "自定义View")
class CustomViewFragment : ViewBindingFragment<FragmentCustomViewBinding>() {

    private var isLoading = true

    override fun viewBindingInflate(
        inflater: LayoutInflater,
        container: ViewGroup
    ) = FragmentCustomViewBinding.inflate(inflater, container, false)

    override fun initViews() {
        binding?.action?.setOnClickListener {
            if (isLoading) {
                binding?.loading?.stop()
                binding?.action?.text = "开始"
            } else {
                binding?.loading?.start()
                binding?.action?.text = "停止"
            }
            isLoading = !isLoading
        }
    }

}