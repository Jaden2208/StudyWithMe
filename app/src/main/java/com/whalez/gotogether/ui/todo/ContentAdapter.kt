package com.whalez.gotogether.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whalez.gotogether.R
import kotlinx.android.synthetic.main.content_item.view.*

class ContentAdapter(private val contentList: ArrayList<String>) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_item, parent, false)
        return ContentViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val content = contentList[position]
        holder.content.text = content
    }

    override fun getItemCount(): Int = contentList.size

    inner class ContentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.tv_content
//        init {
//            itemView.setOnClickListener {
//                if(adapterPosition != RecyclerView.NO_POSITION){
//                    // 높이 확장되게 구현해야 됨.
////                    if(!selectable){
////                        listener.onItemClick(filteredMemos[adapterPosition], itemView)
////                    } else {
////                        toggleItemSelected(adapterPosition)
////                    }
//                }
//            }
//        }
    }
//
//    fun toggleItemSelected(position: Int){
//        if(selectedItems.get(position, false)) {
//            selectedItems.delete(position)
//            notifyItemChanged(position)
//        } else {
//            selectedItems.put(position, true)
//            notifyItemChanged(position)
//        }
//    }
//    private fun isItemSelected(position: Int): Boolean {
//        return selectedItems.get(position,false)
//    }
//    fun clearSelectedItems() {
//        for(position in selectedItems.keyIterator()) {
//            selectedItems.put(position, false)
//            notifyItemChanged(position)
//        }
//        selectedItems.clear()
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(todo: Todo, view: View)
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }
}