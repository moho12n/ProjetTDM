package com.medecin.project

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.ForeignKey


@Entity(tableName = "medecin", foreignKeys = arrayOf(ForeignKey(entity =
Agenda::class, parentColumns = arrayOf("id_agenda"),
    childColumns = arrayOf("id_agenda"),
    onDelete = ForeignKey.CASCADE)))
data class Medecin (
    val full_name : String,
    val commune : String,
    val specialite : String,
    val localisation : String,
    val heure_ouverture : String,
    val heure_fermeture : String
){
    @PrimaryKey (autoGenerate = true)
    var phone : Int?=null
}