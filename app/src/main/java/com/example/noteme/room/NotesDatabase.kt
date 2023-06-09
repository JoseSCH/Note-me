package com.example.noteme.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Model::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun NoteDAO(): NoteDAO

    companion object{

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "bdNotas"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}