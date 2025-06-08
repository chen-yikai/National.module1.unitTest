package com.example.nationalmodule1unittest

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val en: String,
    val tw: String,
    val learning: Boolean
)

@Dao
interface CardDao {
    @Insert
    suspend fun addCard(card: Card)

    @Query("SELECT * FROM cards")
    fun getAllCards(): Flow<List<Card>>

    @Update
    suspend fun updateCard(card: Card)

    @Query("UPDATE cards SET learning = :learning WHERE id = :id")
    suspend fun updateLearning(id: Int, learning: Boolean)
}

@Database(entities = [Card::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}