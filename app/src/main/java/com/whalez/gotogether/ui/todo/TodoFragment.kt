package com.whalez.gotogether.ui.todo

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whalez.gotogether.*
import com.whalez.gotogether.room.data.Todo
import kotlinx.android.synthetic.main.fragment_todo.*

class TodoFragment : Fragment() {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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
        todoViewModel.getAll().observe(viewLifecycleOwner,
            Observer { todoList-> todoAdapter.setTodoList(todoList) }
        )

        // 할 일 추가하기.
        btn_add.setOnClickListener {
            val intent = Intent(activity, AddTodoActivity::class.java)
            startActivityForResult(intent, ADD_TODO_REQUEST)
        }

        // More 버튼 클릭 시.
        todoAdapter.setOnItemClickListener(object : TodoAdapter.OnMoreClickListener {
            override fun onItemClick(todo: Todo, view: View) {
                shortToast(mContext, "More 버튼 클릭")
            }
        })

        // 슬라이드를 통한 아이템 삭제
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder = simpleBuilder(mContext)
                builder.setMessage("할 일을 목록에서 삭제하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("예") { _, _ ->
                        todoViewModel.delete(todoAdapter.getTodoAt(viewHolder.adapterPosition))
                        shortToast(mContext, "삭제되었습니다.")
                    }
                    .setNegativeButton("아니요") { _, _ ->
                        todoAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    .show()
            }
        }).attachToRecyclerView(rv_todo)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK && data != null){
            val timestamp = data.getLongExtra(EXTRA_TIMESTAMP, -1)
            val content = data.getStringExtra(EXTRA_CONTENT)!!

            val todo = Todo(timestamp, content)
            todoViewModel.insert(todo)
        }
    }
}