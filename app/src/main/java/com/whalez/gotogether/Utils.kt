package com.whalez.gotogether

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat.startActivity
import org.joda.time.DateTime

var kakaoLogin = 0

const val ADD_TODO_REQUEST = 0
const val EDIT_TODO_REQUEST = 1

const val EXTRA_ID = "EXTRA_ID"
const val EXTRA_TIMESTAMP = "EXTRA_TIMESTAMP"
const val EXTRA_CONTENT = "EXTRA_CONTENT"

fun goToLoginPage(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
    (context as MainActivity).finish()
}

fun shortToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun simpleBuilder(context: Context): AlertDialog.Builder {
    return AlertDialog.Builder(
        ContextThemeWrapper(
            context,
            R.style.MyAlertDialogStyle
        )
    )
}