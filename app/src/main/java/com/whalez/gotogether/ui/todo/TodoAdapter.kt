package com.whalez.gotogether.ui.todo

import android.animation.ValueAnimator
import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.whalez.gotogether.R
import com.whalez.gotogether.room.data.Todo
import kotlinx.android.synthetic.main.todo_item.view.*
import org.joda.time.DateTime
import kotlin.math.roundToInt


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.MemoViewHolder>() {

    private var todoList: List<Todo> = ArrayList()

    private var isSelectedItem = SparseBooleanArray(0)
    private var prePosition = -1

    private lateinit var context: Context

    private lateinit var listener: OnMoreClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return MemoViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val todo = todoList[position]
        val dateTime = DateTime(todo.timestamp)
        holder.yearMonth.text = dateTime.toString("yyyy.MM")
        holder.date.text = dateTime.toString("dd")
        holder.content.text = todo.content
        holder.changeVisibility(isSelectedItem[position])
        holder.btnMore.visibility = if (isSelectedItem[position]) View.VISIBLE else View.GONE
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
        private val todoItem: CardView = itemView.cv_todo_item
        val yearMonth: TextView = itemView.tv_year_month
        val date: TextView = itemView.tv_date
        val content: TextView = itemView.tv_content
        val btnMore: ImageButton = itemView.btn_more

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (isSelectedItem[adapterPosition]) {
                        isSelectedItem.put(adapterPosition, false)
                    } else {
                        isSelectedItem.put(prePosition, false)
                        isSelectedItem.put(adapterPosition, true)
                    }
                    if (prePosition != -1) notifyItemChanged(prePosition)
                    notifyItemChanged(adapterPosition)
                    prePosition = adapterPosition
                }
            }

            btnMore.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(todoList[adapterPosition], it)
                }
            }
        }

        fun changeVisibility(isExpanded: Boolean) {
            val dpValue = 250
            val d = context.resources.displayMetrics.density
            val maxHeight = (dpValue * d).roundToInt()
            val minHeight =
                context.resources.getDimension(R.dimen.todo_item_min_height).roundToInt()

            val valueAnimator =
                if (isExpanded) ValueAnimator.ofInt(minHeight, maxHeight) else ValueAnimator.ofInt(
                    maxHeight,
                    minHeight
                )
            valueAnimator.duration = 200
            valueAnimator.addUpdateListener {
                val heightValue = it.animatedValue as Int
                todoItem.apply {
                    layoutParams.height = heightValue
                    requestLayout()
                }
            }
            valueAnimator.start()
        }
    }

    interface OnMoreClickListener {
        fun onItemClick(todo: Todo, view: View)
    }

    fun setOnItemClickListener(listener: OnMoreClickListener) {
        this.listener = listener
    }

}