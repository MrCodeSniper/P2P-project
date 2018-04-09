package com.example.mac.back.bean

/**
 * Created by mac on 2018/4/7.
 */
open class BaseBean_Kotlin(var code: Int,var success: Boolean,var msg: String)

//编译器在背后默默给我们生成了如下的东西：

//equals()/hashCode()
//toString()方法
//componentN()方法
//copy()方法