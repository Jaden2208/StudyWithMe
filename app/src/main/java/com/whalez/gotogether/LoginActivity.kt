package com.whalez.gotogether

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var userNickname: String
    private lateinit var userProfileImgUrl: String

    private val sessionCallback = object : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.d("kkk", "로그인 실패: ${exception.toString()}")
        }

        override fun onSessionOpened() {
            if (kakaoLogin == 0) {
                saveUserInfoToFirestore()
                kakaoLogin = 1
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 세션 콜백 등록
        Session.getCurrentSession().addCallback(sessionCallback)

    }

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun saveUserInfoToFirestore() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                if (result == null) {
                    Log.d("kkkGetUserInfo", "result == null")
                    return
                }
                val kakaoProfile = result.kakaoAccount.profile
                userNickname = kakaoProfile.nickname
                userProfileImgUrl = kakaoProfile.profileImageUrl
                val uuid = UUID.randomUUID().toString()
                val user = hashMapOf(
                    "uuid" to uuid,
                    "nickname" to userNickname,
                    "profile_img_url" to userProfileImgUrl,
                    "group" to ""
                )

                Log.d("kkkNickName", user["nickname"].toString())

//                val firestore = FirebaseFirestore.getInstance()
//                firestore.collection("users").add(user)
//                    .addOnSuccessListener {
//                        Log.d("kkk", "DocumentSnapshot added with ID: ${it.id}")
//                    }
//                    .addOnFailureListener {
//                        Log.d("kkk", "fail to save user info")
//                    }
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.d("kkkSessionClosed", errorResult!!.errorMessage)
            }

        })
    }


}
