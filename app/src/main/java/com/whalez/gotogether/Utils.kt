package com.whalez.gotogether

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

var kakaoLogin = 0

fun goToLoginPage(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
    (context as MainActivity).finish()
}
