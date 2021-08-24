package com.android.stitch.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.android.stitch.model.Ukuran

@Dao
interface DaoUkuran {

    @Insert(onConflict = REPLACE)
    fun insert(data: Ukuran)

    @Delete
    fun delete(data: Ukuran)

    @Update
    fun update(data: Ukuran): Int

    @Query("SELECT * from ukuran ORDER BY id ASC")
    fun getAll(): List<Ukuran>

    @Query("SELECT * FROM ukuran WHERE id = :id LIMIT 1")
    fun getUkuran(id: Int): Ukuran

    @Query("SELECT * FROM ukuran WHERE isSelected = :status LIMIT 1")
    fun getByStatus(status: Boolean): Ukuran?

    @Query("DELETE FROM ukuran")
    fun deleteAll(): Int
}