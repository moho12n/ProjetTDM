package com.medecin.project

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "agenda")
data class RendezVous(
    @PrimaryKey
    var id:Int,
    var date : String,
    val patient: String,
    val medecin: String
)