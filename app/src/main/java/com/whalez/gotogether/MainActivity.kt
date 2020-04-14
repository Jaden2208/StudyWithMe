package com.whalez.gotogether

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("kkkMainActivity", "onCreate")

//        Log.d("kkk로그인여부?", Session.getCurrentSession().checkAndImplicitOpen().toString())
        checkUserLoginStatus(this@MainActivity)


        btn_logout.setOnClickListener {
            UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                override fun onCompleteLogout() {
                    kakaoLogin = 0
                    goToLoginPage(this@MainActivity)
                    Toast.makeText(this@MainActivity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun checkUserLoginStatus(context: Context){
        val userIsLoggedIn = Session.getCurrentSession().checkAndImplicitOpen()
        if(!userIsLoggedIn) goToLoginPage(context)
        else return
    }

    private fun goToLoginPage(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
