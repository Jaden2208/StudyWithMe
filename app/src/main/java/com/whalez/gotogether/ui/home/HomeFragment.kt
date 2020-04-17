package com.whalez.gotogether.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.whalez.gotogether.R
import com.whalez.gotogether.ui.todo.TodoAdapter
import com.whalez.gotogether.ui.todo.TodoViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.joda.time.DateTime

class HomeFragment : Fragment() {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val year = DateTime().year
        val month = DateTime().monthOfYear
        val day = DateTime().dayOfMonth
        var timestamp = DateTime(year, month, day, 0, 0, 0).millis

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.getTodaysTodo(timestamp).observe(viewLifecycleOwner,
            Observer { content -> tv_today_todo.text = content })
    }
}