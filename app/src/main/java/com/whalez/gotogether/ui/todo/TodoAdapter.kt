package com.whalez.gotogether.ui.todo

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.keyIterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whalez.gotogether.R
import com.whalez.gotogether.room.data.Todo
import kotlinx.android.synthetic.main.todo_item.view.*
import org.joda.time.DateTime

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.MemoViewHolder>() {

    private var todoList: List<Todo> = ArrayList()
    private lateinit var listener: OnItemClickListener
    var selectable = false
    private var selectedItems = SparseBooleanArray(0)
    var selectedItemsIds = ArrayList<Int>()

    lateinit var contentAdapter: ContentAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return MemoViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val todo = todoList[position]
//        holder.content.text = currentMemo.content
        val dateTime = DateTime(todo.timestamp)
        val contentList = todo.content
        holder.yearMonth.text = dateTime.toString("yyyy.MM")
        holder.date.text = dateTime.toString("dd")
        holder.content.apply {
            contentAdapter = ContentAdapter(contentList)
            layoutManager = LinearLayoutManager(context)
            adapter = contentAdapter
//            setHasFixedSize(true)
        }

        val isMemoSelected = isItemSelected(position)
        if (isMemoSelected) {
            selectedItemsIds.add(todo.id)
        } else {
            selectedItemsIds.remove(todo.id)
        }
        holder.itemView.isSelected = isMemoSelected
    }

    override fun getItemCount(): Int = todoList.size

    fun getTodoAt(position: Int): Todo {
        return todoList[position]
    }

    fun setTodoList(todoList: List<Todo>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    inner class MemoViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val yearMonth: TextView = itemView.tv_year_month
        val date: TextView = itemView.tv_date
        val content: RecyclerView = itemView.rv_content
        init {
            itemView.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    // 높이 확장되게 구현해야 됨.
//                    if(!selectable){
//                        listener.onItemClick(filteredMemos[adapterPosition], itemView)
//                    } else {
//                        toggleItemSelected(adapterPosition)
//                    }
                }
            }
        }
    }

    fun toggleItemSelected(position: Int){
        if(selectedItems.get(position, false)) {
            selectedItems.delete(position)
            notifyItemChanged(position)
        } else {
            selectedItems.put(position, true)
            notifyItemChanged(position)
        }
    }
    private fun isItemSelected(position: Int): Boolean {
        return selectedItems.get(position,false)
    }
    fun clearSelectedItems() {
        for(position in selectedItems.keyIterator()) {
            selectedItems.put(position, false)
            notifyItemChanged(position)
        }
        selectedItems.clear()
    }

    interface OnItemClickListener {
        fun onItemClick(todo: Todo, view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}