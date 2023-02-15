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

## 主题




## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/pay/pay.png)

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![](https://s1.ax1x.com/2022/04/27/LbGMJH.jpg)