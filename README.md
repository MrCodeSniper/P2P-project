
![](https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2524887731,4046843904&fm=58&s=30946833F2E44CA22F419CDA0000A022&bpow=121&bpoh=75)

XX猫-金融类app
=========================
[![Build Status](https://travis-ci.org/meolu/walle-web.svg?branch=master)](https://travis-ci.org/meolu/walle-web)
[![Packagist](https://img.shields.io/packagist/v/meolu/walle-web.svg)](https://packagist.org/packages/meolu/walle-web)
[![Yii2](https://img.shields.io/badge/Powered_by-Yii_Framework-green.svg?style=flat)](http://www.yiiframework.com/)

2018 first app Thanks for your review.

[Home Page](https://github.com/MrCodeSniper/) | [官方主页](https://github.com/MrCodeSniper/) | [中文说明](https://github.com/MrCodeSniper/) | [文档手册](https://github.com/MrCodeSniper/).

Now, there are more than hundreds of companies hosted xxcat for deployment, star it if you like : )

* Support git/svn Version control system.
* User signup by admin/develop identity.
* Developer submit a task, deploy task.
* Admin audit task.
* Multiple project.
* Multiple Task Parallel.
* Quick rollback.
* Group relation of project.
* Task of pre-deploy（e.g: test ENV var）.
* Task of post-deploy（e.g: mvn/ant, composer install for vendor）.
* Task of pre-release（e.g: stop service）.
* Task of post-release（e.g: restart service）.
* Check up file md5.
* Multi-process multi-server file transfer (Ansible).


Requirements
------------

* androidstudio
* eclipse for javaee
* Mysql sqyog
* genymotion

That's all. It's base package of java environment!

Skills
----------

- http-Retrofit+okhttp
- Frame-MVC
- Gray released：specify servers
- Websocket instead of poll
- A manager of static source
- Configure variables
- Support Docker
- Open api
- Command line


Screenshots
-----------

#### homepage
![](https://upload-images.jianshu.io/upload_images/2634235-9180aef028a26c7b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/588)

#### productpage
![](https://upload-images.jianshu.io/upload_images/2634235-d9482b0127767197.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/588)

#### morepage
![](https://upload-images.jianshu.io/upload_images/2634235-fe35dabb031abac4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/588)

#### actionpage
![](https://upload-images.jianshu.io/upload_images/2634235-d2a51a0aca5399dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/588)

## CHANGELOG

-v1.0
day1.android:
- 首页模块 新手乐园+安全保障嵌入html并两端互调 实现hybird混合开发
- 个人模块 实现登陆注册机制 发送短信和验证短信功能

day1.javaserver:
- html中添加调用android代码 并正常访问html活动页判断用户是否在安卓端登陆
- loginserverlet  登陆接口正常使用
- registerservlet 注册接口正常使用
- sendSmsServlet smsVerifyServlet 短信验证码发送和验证接口 集成短信平台sdk

day2.android:
-  完成注册登陆后用户界面的显示 包括用户信息与用户活动
-  用户活动页添加了可拖拽的网格视图 方便自定义功能

day3.android
- 增加了自定义未捕获日常 并能将异常保存为文件上传到服务器
- 保存了用户信息在app本地，更新用户在登陆后相应的界面变化

day4.android(安全专题)
- 通常android加密有两种实现 1.在java代码里 写个加密算法直接调用 2.使用ndk 在native中使用c的加密算法
- 第一种容易被反编译 即使使用第三方加固软件 也会被脱固 这在金融app中是不容许的
- 但第二种来说app被反编译是很容易得到.so文件的 只需要知道如何调用.so不管其采取什么算法 也是存在风险的
- 固本app在使用aes加密方法之上进行签名校验 防止二次打包



Discussing
----------
- [submit issue](https://github.com/MrCodeSniper/)
- email: 15168264355@163.com

勾搭下
--------
<img src="http://upload-images.jianshu.io/upload_images/2634235-333dfbca6ecdd6ba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" width="244" height="314" alt="我的微信" align=left />
