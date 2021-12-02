package com.envision.assignment.repository.offline

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDocument(document: Document)

    @Query("SELECT * FROM documents")
    fun getAllDocuments(): Flow<List<Document>>
}