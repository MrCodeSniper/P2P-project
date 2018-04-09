package com.example.mac.back.bean

/**
 * Created by mac on 2018/4/7.
 */

data class DataBean(var accounts: Double,var cat_food: Int,var id: Int,var recharge: Double,var reward_coin: Int)

data class UserBank_Kotlin(var data: DataBean,var code: Int,var success: Boolean,var msg: String)