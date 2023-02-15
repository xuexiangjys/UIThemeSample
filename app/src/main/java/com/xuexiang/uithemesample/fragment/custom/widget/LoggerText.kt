/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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
package com.xuexiang.uithemesample.fragment.custom.widget

import android.content.Context
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.xuexiang.uithemesample.R
import com.xuexiang.xui.utils.ResUtils

/**
 * 日志打印显示控件【继承组件的方式】
 *
 * @author xuexiang
 * @since 2020/12/10 12:32 AM
 */
class LoggerText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.LoggerTextStyle
) : AppCompatTextView(
    context, attrs, defStyleAttr
) {
    /**
     * 日志格式化接口
     */
    private var mLogFormatter: ILogFormatter? = DefaultLogFormatter()

    /**
     * 日志装饰接口
     */
    private var mLogDecorator: ILogDecorator? = DefaultLogDecorator()

    /**
     * 设置日志格式化接口
     *
     * @param logFormatter 日志格式化接口
     * @return 日志打印显示控件
     */
    fun setLogFormatter(logFormatter: ILogFormatter): LoggerText {
        mLogFormatter = logFormatter
        return this
    }

    /**
     * 设置日志装饰接口
     *
     * @param logDecorator 日志装饰接口
     * @return 日志打印显示控件
     */
    fun setLogDecorator(logDecorator: ILogDecorator): LoggerText {
        mLogDecorator = logDecorator
        return this
    }

    /**
     * 添加普通日志
     *
     * @param logContent 日志内容
     */
    fun logNormal(logContent: String) {
        addLog(logContent, LogType.NORMAL)
    }

    /**
     * 添加成功日志
     *
     * @param logContent 日志内容
     */
    fun logSuccess(logContent: String) {
        addLog(logContent, LogType.SUCCESS)
    }

    /**
     * 添加错误日志
     *
     * @param logContent 日志内容
     */
    fun logError(logContent: String) {
        addLog(logContent, LogType.ERROR)
    }

    /**
     * 添加警告日志
     *
     * @param logContent 日志内容
     */
    fun logWarning(logContent: String) {
        addLog(logContent, LogType.WARNING)
    }

    /**
     * 添加自定义等级日志
     *
     * @param logContent 日志内容
     */
    fun logCustom(logContent: String) {
        addLog(logContent, LogType.CUSTOM)
    }

    /**
     * 添加日志
     *
     * @param logContent 日志内容
     * @param logType    日志类型
     */
    fun addLog(logContent: String, logType: LogType?) {
        val spannableString =
            logDecorator.decorate(logFormatter.format(logContent, logType), logType)
        appendLogInMainThread(spannableString)
    }

    private fun appendLogInMainThread(spannableString: SpannableString) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            appendLog(spannableString)
        } else {
            post { appendLog(spannableString) }
        }
    }


    private val logDecorator by lazy {
        DefaultLogDecorator()
    }

    /**
     * 获取日志格式化接口
     *
     * @return 日志格式化接口
     */
    private val logFormatter by lazy {
        DefaultLogFormatter()
    }

    private fun appendLog(spannableString: SpannableString) {
        append(spannableString)
        append("\r\n")
        scrollToEnd()
    }

    /**
     * 滑动至最后一行
     */
    private fun scrollToEnd() {
        val offset = textRealHeight
        if (offset > height) {
            scrollTo(0, offset - height)
        }
    }

    /**
     * @return 获取当前TextView文字的真实高度
     */
    private val textRealHeight: Int
        get() {
            val layout = layout
            return (layout?.getLineTop(lineCount) ?: 0) + compoundPaddingTop + compoundPaddingBottom
        }

    /**
     * 清除日志
     */
    fun clearLog() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            text = ""
            scrollTo(0, 0)
        } else {
            post {
                text = ""
                scrollTo(0, 0)
            }
        }
    }

    /**
     * 默认日志格式化接口
     *
     * @author xuexiang
     * @since 2021/4/14 1:48 AM
     */
    class DefaultLogFormatter : ILogFormatter {
        override fun format(logContent: String, logType: LogType?): String {
            return logContent
        }
    }

    /**
     * 日志格式化接口
     *
     * @author xuexiang
     * @since 2021/4/14 1:30 AM
     */
    interface ILogFormatter {
        /**
         * 格式化日志内容
         *
         * @param logContent 日志内容
         * @param logType    日志类型
         * @return 格式化后的日志
         */
        fun format(logContent: String, logType: LogType?): String
    }

    /**
     * 默认日志装饰接口
     *
     * @author xuexiang
     * @since 2021/4/14 1:49 AM
     */
    class DefaultLogDecorator : ILogDecorator {
        override fun decorate(logContent: String, logType: LogType?): SpannableString {
            val spannableString = SpannableString(logContent)
            val colorSpan = when (logType) {
                LogType.ERROR -> ForegroundColorSpan(ResUtils.getColor(R.color.xui_config_color_error))
                LogType.SUCCESS -> ForegroundColorSpan(ResUtils.getColor(R.color.xui_config_color_success))
                LogType.WARNING -> ForegroundColorSpan(ResUtils.getColor(R.color.xui_config_color_waring))
                else -> null
            }
            colorSpan?.let {
                spannableString.setSpan(
                    it, 0,
                    logContent.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannableString
        }
    }

    /**
     * 日志装饰接口
     *
     * @author xuexiang
     * @since 2021/4/14 1:30 AM
     */
    interface ILogDecorator {
        /**
         * 装饰日志内容
         *
         * @param logContent 日志内容
         * @param logType    日志类型
         * @return 装饰后的日志
         */
        fun decorate(logContent: String, logType: LogType?): SpannableString
    }

    /**
     * 日志类型
     */
    enum class LogType {
        /**
         * 普通日志
         */
        NORMAL,

        /**
         * 成功日志
         */
        SUCCESS,

        /**
         * 出错日志
         */
        ERROR,

        /**
         * 警告日志
         */
        WARNING,

        /**
         * 自定义等级日志
         */
        CUSTOM
    }

    init {
        movementMethod = ScrollingMovementMethod()
    }
}