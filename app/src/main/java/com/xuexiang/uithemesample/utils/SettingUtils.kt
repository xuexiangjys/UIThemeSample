package com.xuexiang.uithemesample.utils

/**
 *
 *
 * @author xuexiang
 * @since 2023/2/22 23:17
 */
object SettingUtils {
    private const val IS_USE_CUSTOM_THEME_KEY = "is_use_custom_theme_key"
    /**
     * 是否是第一次启动
     */
    /**
     * 设置是否是第一次启动
     */
    var isUseCustomTheme: Boolean
        get() = MMKVUtils.getBoolean(IS_USE_CUSTOM_THEME_KEY, true)
        set(value) {
            MMKVUtils.put(IS_USE_CUSTOM_THEME_KEY, value)
        }

    fun switchTheme() {
        isUseCustomTheme = !isUseCustomTheme
    }
}