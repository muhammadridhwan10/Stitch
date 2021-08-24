package com.android.stitch.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.android.stitch.model.Model

@Dao
interface DaoKeranjang {

    @Insert(onConflict = REPLACE)
    fun insert(data: Model)

    @Delete
    fun delete(data: Model)

    @Delete
    fun delete(data: List<Model>)

    @Update
    fun update(data: Model): Int

    @Query("SELECT * from cart ORDER BY id ASC")
    fun getAll(): List<Model>

    @Query("SELECT * FROM cart WHERE id = :id LIMIT 1")
    fun getModel(id: Int): Model

    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM cart")
    fun deleteAll(): Int
}