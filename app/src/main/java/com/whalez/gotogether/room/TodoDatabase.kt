package com.whalez.gotogether.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.whalez.gotogether.room.data.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}