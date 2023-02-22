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

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xuexiang.uithemesample.R
import com.xuexiang.uithemesample.core.ViewBindingFragment
import com.xuexiang.uithemesample.databinding.FragmentStyleRuleBinding
import com.xuexiang.uithemesample.utils.SettingUtils
import com.xuexiang.uithemesample.widget.CustomTextView.Companion.LARGE
import com.xuexiang.xpage.annotation.Page
import com.xuexiang.xui.utils.ResUtils
import com.xuexiang.xui.widget.actionbar.TitleBar
import com.xuexiang.xutil.display.Colors

/**
 * Style的优先级顺序:
 *
 * 1.通过文本span将字符设置的样式应用到TextView派生的类。
 * 2.以代码方式动态设置的属性。
 * 3.将单独的属性直接应用到View。
 * 4.将样式应用到View。
 * 5.控件的默认样式，在View构造方法中定义的。
 * 6.控件所处应用、Activity、父布局所应用的主题。
 * 7.应用某些特定于View的样式，例如为TextView设置TextAppearance。
 *
 * @author xuexiang
 * @since 2022/7/7 1:18 上午
 */
@Page(name = "Style的优先级顺序")
class StyleRuleFragment : ViewBindingFragment<FragmentStyleRuleBinding>() {

    override fun viewBindingInflate(
        inflater: LayoutInflater,
        container: ViewGroup
    ) = FragmentStyleRuleBinding.inflate(inflater, container, false)

    override fun initTitle(): TitleBar? {
        val titleBar = super.initTitle()
        titleBar?.addAction(object : TitleBar.TextAction("切换主题") {
            override fun performAction(view: View?) {
                SettingUtils.switchTheme()
                activity?.recreate()
            }
        })
        return titleBar
    }

    private val textSample = ResUtils.getString(R.string.label_text_content_sample)

    override fun initViews() {
        setSpanStyle()

        setCodingStyle()
    }

    private fun setSpanStyle() {
        val ss = SpannableString(textSample)
        ss.setSpan(ForegroundColorSpan(Colors.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(AbsoluteSizeSpan(28, true), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding?.ctvSpan?.run {
            text = ss
            textSize = 14F
        }
    }

    private fun setCodingStyle() {
        binding?.ctvCoding?.run {
            isPassword = false
            size = LARGE
        }
    }


}