# UIThemeSample

Android的UI主题使用案例

## 关于我

| 公众号   | 掘金     |  知乎    |  CSDN   |   简书   |   思否  |   哔哩哔哩  |   今日头条
|---------|---------|--------- |---------|---------|---------|---------|---------|
| [我的Android开源之旅](https://t.1yb.co/Irse)  |  [点我](https://juejin.im/user/598feef55188257d592e56ed/posts)    |   [点我](https://www.zhihu.com/people/xuexiangjys/posts)       |   [点我](https://xuexiangjys.blog.csdn.net/)  |   [点我](https://www.jianshu.com/u/6bf605575337)  |   [点我](https://segmentfault.com/u/xuexiangjys)  |   [点我](https://space.bilibili.com/483850585)  |   [点我](https://img.rruu.net/image/5ff34ff7b02dd)

## 自定义View

### 完全自定义View实现自定义控件

自定义View或者自定义ViewGroup：

* 自定义View：主要重写onDraw（绘制）方法。

* 自定义ViewGroup：主要重写：onMeasure（测量）、onLayout（布局）这两个方法。

### 继承组件的方式实现自定义控件

最简单的自定义组件的方式，直接继承需要拓展/修改的控件，重写对应的方法即可。

一般是希望在原有系统控件基础上做一些修饰性的修改（功能增强），而不会做大幅度的改动。

### 组合的方式实现自定义控件

> 组合控件就是将多个控件组合成一个新的控件，可以重复使用。

实现组合控件的一般步骤如下：

* 编写布局文件
* 实现构造方法
* 初始化UI，加载布局
* 对外提供修改的接口api

可以看到，组合的方式和我们平时写一个Fragment的流程是很类似的。

## Theme主题

应用于窗体级别，是一整套样式的组合，采取就近原则：Application > Activity > ViewGroup > View。 一般而言，Theme主要应用于Application和Activity这样的窗体，主要放在`/res/values/themes.xml`。

```xml
<resources>
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
```

### Application中的Theme

Application的主题一般在`Manifest`中，它只对在`Manifest`中未设置Theme的Activity生效。

```xml
<application android:theme="@style/AppTheme">

</application>
```

### Activity中的Theme

Activity的主题可以在`Manifest`和代码中调用`setTheme`设置。一般在Activity的onCreate()中，`setContentView`方法之前设置。

1.在`Manifest`中设置。

```xml
<activity android:theme="@style/DialogTheme">

</activity>
```

2.代码中调用`setTheme`设置，注意一定要在调用`setContentView(View)`和`inflate(int, ViewGroup)`方法前。

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.AppTheme)
    setContentView(R.layout.layout_main)
}
```

### ViewGroup和View中的Theme

ViewGroup和View的主题一般在布局xml中设置，使用`android:theme`设置。

```xml
<ViewGroup 
    android:theme="@style/ThemeOverlay.App.Foo">
    
    <Button android:theme="@style/ThemeOverlay.App.Bar" />
    
</ViewGroup>
```

## Style样式

> 仅应用于单个View这种窗体元素级别的外观，主要放在`/res/values/styles.xml`。

### Style的声明

### Style的使用

样式一般在布局xml中设置，使用`android:style`设置，不同于主题，样式只能应用于单个View，对于其子View并不会生效。

```xml
<ViewGroup 
    android:style="@style/ActionContainerStyle">
    
    <Button android:style="@style/BlueButtonStyle" />
    
</ViewGroup>
```

### Style的优先级顺序

如果我们在多个地方给控件指定了style的属性，那么最终是由谁生效呢？这里我们就以TextView为例，介绍一下Style的生效规则：

* 1.通过文本span将字符设置的样式应用到TextView派生的类。
* 2.以代码方式动态设置的属性。
* 3.将单独的属性直接应用到View。
* 4.将样式应用到View。
* 5.控件的默认样式，在View构造方法中定义的。
* 6.控件所处应用、Activity、父布局所应用的主题。
* 7.应用某些特定于View的样式，例如为TextView设置TextAppearance。

## Attribute属性

> Attribute属性是组成Style的基本单位。如果说主题是各种样式的组合，那么样式就是各种属性的组合，主要放在`/res/values/attrs.xml`。

### Attribute的声明

1.单个属性的定义

```xml
<resource>

    <attr name="attr-name" format="format-type" />

</resource>
```

2.一组属性的定义

```xml
<resource>

    <declare-styleable name="XXXXView">
        <attr name="attr-name" format="format-type" />
        <attr name="attr-name" format="format-type" />
    </declare-styleable>

</resource>
```

3.属性的赋值

```xml
<style name="xx">

  <item name="attr-name">value</item>

</style>
```

### Attribute的使用



### Attribute的获取


## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/pay/pay.png)

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![](https://s1.ax1x.com/2022/04/27/LbGMJH.jpg)