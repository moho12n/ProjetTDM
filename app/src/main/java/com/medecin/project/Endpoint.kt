package com.medecin.project


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
    // Call<List<Player>: une fonction callback qui retourne une liste


    @POST("sendSMS")
    fun envoyerSMS(@Body patient: Patient) : Call<String>

    @POST("/patient/insert")

    fun addpatient(@Body patient: Patient):Call<String>

    @POST("patient/update/{phone}/{password}")
    fun update ( @Path("phone" ) phone:String,@Path( "password") pass:String):Call<String>


    @GET("login/{phone}/{password}")
    fun login(@Path("phone") name:String ,@Path( "password") pass:String):Call<List<Patient>>


    @GET("getMedecin")
    fun getmedecin(): Call<List<Medecin>>
    @GET("getMedecinCommune/{commune}")
    fun getmedecinCommune(@Path ("commune") isbn:String): Call<List<Medecin>>
    @GET("getMedecinSpecialite/{specialite}")
    fun getmedecinSpecialite(@Path ("specialite") isbn:String): Call<List<Medecin>>
    @GET("getMedecin/{commune}/{specialite}")
    fun getMedecinSpecAndComm(@Path ("commune") isbn:String,@Path ("specialite") isbn2:String): Call<List<Medecin>>
    @GET("getMedTraitant/{phone}/{statut}")
    fun getMedTraitant(@Path ("phone") isbn:String,@Path ("statut") isbn2:String): Call<List<Medecin>>

    @POST("patient/demandetraitement")
    fun ajouterMedecinTraitant(@Body demande: DemandeAjout):Call<String>

    @GET("getRDV/{patient}")
    fun getRdv(@Path ("patient") isbn:String): Call<List<RendezVous>>

}
