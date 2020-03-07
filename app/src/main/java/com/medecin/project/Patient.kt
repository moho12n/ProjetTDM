package com.medecin.project

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.ForeignKey
import java.sql.Time

@Entity (tableName = "patient")

data class Patient (
    val NSS : Int,
    val Nom : String,
    val Prenom : String,
    val adresse : String,
    val password : String,
    val newpassword : String,
    @PrimaryKey
    var phone : String
)

