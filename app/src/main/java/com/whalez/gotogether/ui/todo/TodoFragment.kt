package com.whalez.gotogether.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.whalez.gotogether.R
import com.whalez.gotogether.room.data.Todo
import kotlinx.android.synthetic.main.fragment_todo.*

class TodoFragment : Fragment() {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_todo.apply {
            todoAdapter = TodoAdapter()
            layoutManager = LinearLayoutManager(activity)
            adapter = todoAdapter
            setHasFixedSize(true)
        }

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.getAll().observe(this,
            Observer { todoList -> todoAdapter.setTodoList(todoList) }
        )


    }
}