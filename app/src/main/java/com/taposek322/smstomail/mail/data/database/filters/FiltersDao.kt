package com.taposek322.smstomail.mail.data.database.filters

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FiltersDao {

    @Insert
    fun insertIn(filterData: FiltersData)

    @Update
    fun updateData(filterData: FiltersData)

    @Delete
    fun delete(filterData: FiltersData)

    @Transaction
    @Query("SELECT * FROM filtersdata")
    fun selectAll(): Flow<List<FilterWithMail>>

    @Query("SELECT * from filtersdata where id = :id")
    fun getFiltersData(id:Int):Flow<FiltersData>
}