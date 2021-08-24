package com.android.stitch.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.stitch.model.Model
import com.android.stitch.model.Ukuran

@Database(entities = [Model::class, Ukuran::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoCart(): DaoKeranjang // DaoNote
    abstract fun daoUkuran() : DaoUkuran

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MyDatabase::class.java, "DatabaseStith1920" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}