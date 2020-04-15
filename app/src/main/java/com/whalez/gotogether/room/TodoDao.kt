package com.whalez.gotogether.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.whalez.gotogether.room.data.Todo

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("select * from Todo order by timestamp asc")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("delete from Todo")
    fun deleteAllTodo()

    @Query("delete from Todo where id in (:idList)")
    fun deleteSelectedTodo(idList: List<Int>)
}