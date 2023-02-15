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
import com.xuexiang.uithemesample.databinding.FragmentExtendViewBinding
import com.xuexiang.uithemesample.fragment.custom.widget.LoggerText
import com.xuexiang.xpage.annotation.Page
import com.xuexiang.xutil.data.DateUtils
import java.text.SimpleDateFormat

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
        // 这里设置自定义的日志格式
        binding?.logger?.setLogFormatter(object : LoggerText.ILogFormatter {
            override fun format(logContent: String, logType: LoggerText.LogType?): String {
                return DateUtils.getNowString(
                    SimpleDateFormat("[HH:mm]")
                ) + logContent
            }
        })

        binding?.btnNormal?.setOnClickListener {
            binding?.logger?.logNormal("这是一条普通日志！")
        }
        binding?.btnSuccess?.setOnClickListener {
            binding?.logger?.logSuccess("这是一条成功日志！")
        }
        binding?.btnError?.setOnClickListener {
            binding?.logger?.logError("这是一条出错日志！")
        }
        binding?.btnWarning?.setOnClickListener {
            binding?.logger?.logWarning("这是一条警告日志！")
        }
        binding?.btnClear?.setOnClickListener {
            binding?.logger?.clearLog()
        }
    }




}