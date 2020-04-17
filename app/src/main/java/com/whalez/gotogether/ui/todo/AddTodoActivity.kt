package com.whalez.gotogether.ui.todo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.whalez.gotogether.EXTRA_CONTENT
import com.whalez.gotogether.EXTRA_TIMESTAMP
import com.whalez.gotogether.R
import com.whalez.gotogether.shortToast
import kotlinx.android.synthetic.main.activity_add_todo.*
import org.joda.time.DateTime
import java.util.*

class AddTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val year = DateTime().year
        val month = DateTime().monthOfYear
        val day = DateTime().dayOfMonth
        var timestamp = DateTime(year, month, day, 0, 0, 0).millis
        btn_show_date_picker.text = DateTime().toString("yyyy년 MM월 dd일")
        btn_show_date_picker.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    val changedTime = DateTime(year, month, day, 0, 0, 0)
                    timestamp = changedTime.millis
                    btn_show_date_picker.text = changedTime.toString("yyyy년 MM월 dd일")
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        btn_back.setOnClickListener { finish() }

        btn_save.setOnClickListener {
            val content = et_content.text.toString()
            if (content.isEmpty()) {
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
