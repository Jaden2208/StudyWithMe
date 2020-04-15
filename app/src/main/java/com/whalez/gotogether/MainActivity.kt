package com.whalez.gotogether

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUserLoginStatus(this@MainActivity)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, HomeFragment()).commit()
        expandable_bottom_bar.onItemSelectedListener = { v, menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.home -> HomeFragment()
                R.id.schedule -> ScheduleFragment()
                R.id.group -> GroupFragment()
                R.id.setting -> SettingFragment()
                else -> null
            }
            if (fragment != null) {
                showAnimation(v, menuItem)
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
            }

        }

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

    private fun checkUserLoginStatus(context: Context) {
        val userIsLoggedIn = Session.getCurrentSession().checkAndImplicitOpen()
        if (!userIsLoggedIn) goToLoginPage(context)
        else return
    }

    private fun goToLoginPage(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showAnimation(v: View, i: ExpandableBottomBarMenuItem){
        val anim = ViewAnimationUtils.createCircularReveal(fragment,
            expandable_bottom_bar.x.toInt() + v.x.toInt() + v.width / 2,
            expandable_bottom_bar.y.toInt() + v.y.toInt() + v.height / 2, 0F,
            findViewById<View>(android.R.id.content).height.toFloat())
        fragment.setBackgroundColor(ColorUtils.setAlphaComponent(i.activeColor, 60))
        anim.duration = 420
        anim.start()
    }
}
