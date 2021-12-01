package com.envision.assignment.roomtest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey(autoGenerate = true)
    val documentId: Int = 0,

    @ColumnInfo(name = "name")
    val documentName: String,

    @ColumnInfo(name = "content")
    val documentText: String
)