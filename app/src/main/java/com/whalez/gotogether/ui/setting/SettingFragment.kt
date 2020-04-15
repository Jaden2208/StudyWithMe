package com.whalez.gotogether.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.whalez.gotogether.R
import com.whalez.gotogether.goToLoginPage
import com.whalez.gotogether.kakaoLogin
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_logout.setOnClickListener {
            UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                override fun onCompleteLogout() {
                    kakaoLogin = 0
                    goToLoginPage(activity!!)
                }
            })
        }
    }


//    private fun goToLoginPage(context: Context) {
//        val intent = Intent(context, LoginActivity::class.java)
//        startActivity(intent)
//        activity!!.finish()
//    }
}