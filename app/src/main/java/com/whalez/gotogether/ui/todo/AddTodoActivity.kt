package com.whalez.gotogether.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.whalez.gotogether.EXTRA_CONTENT
import com.whalez.gotogether.EXTRA_TIMESTAMP
import com.whalez.gotogether.R
import com.whalez.gotogether.shortToast
import kotlinx.android.synthetic.main.activity_add_todo.*
import org.joda.time.DateTime

class AddTodoActivity : AppCompatActivity() {

//    private val contentList = ArrayList<String>()
//    // 어댑터 부터 만들기.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        var timestamp = DateTime().millis
        tv_year_month_date.text = DateTime(timestamp).toString("yyyy년 MM월 dd일")
        tv_year_month_date.setOnClickListener {
            // 날짜 수정.
        }

        btn_back.setOnClickListener { finish() }

        btn_save.setOnClickListener {
            val content = tv_content.text.toString()
            if(content.isEmpty()) {
                shortToast(this, "저장할 내용이 없습니다!")
                return@setOnClickListener
            }
            intent.putExtra(EXTRA_TIMESTAMP, timestamp)
                .putExtra(EXTRA_CONTENT, content)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}
