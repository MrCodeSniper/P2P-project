# 金融类app
更新进度（附java服务器进度）
day1.
android:
首页模块 新手乐园+安全保障嵌入html并两端互调 实现hybird混合开发
个人模块 实现登陆注册机制 发送短信和验证短信功能



javaserver:
html中添加调用android代码 并正常访问html活动页判断用户是否在安卓端登陆
loginserverlet  登陆接口正常使用
registerservlet 注册接口正常使用
sendSmsServlet smsVerifyServlet 短信验证码发送和验证接口 集成短信平台sdk


![](https://raw.github.com/meolu/walle-web/master/docs/logo.jpg)

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



    ```


To Do List
----------

- Travis CI integration
- Mail events：specify kinds of events
- Gray released：specify servers
- Websocket instead of poll
- A manager of static source
- Configure variables
- Support Docker
- Open api
- Command line

Update
-----------------
```
./yii walle/upgrade    # upgrade walle
```


Architecture
------------
#### git/svn, user, host, servers
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-flow-relation-en.png)

#### deployment flow
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-flow-en.png)

Screenshots
-----------

#### project config
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-config-edit-en.jpg)

#### sumbit a task
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-submit-en.jpg)

#### list of task
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-dev-list-en.jpg)

#### demo show
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/en/static/walle-en.gif)

## CHANGELOG
[CHANGELOG](https://github.com/meolu/walle-web/releases)


Discussing
----------
- [submit issue](https://github.com/meolu/walle-web/issues/new)
- email: wushuiyong@huamanshu.com

勾搭下
--------
<img src="https://raw.githubusercontent.com/meolu/walle-web/feature-weixin/docs/weixin.wushuiyong.jpg" width="244" height="314" alt="吴水永微信" align=left />
