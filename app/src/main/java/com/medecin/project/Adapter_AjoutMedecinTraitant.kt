package com.medecin.project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.medecin.project.MainActivity
import com.medecin.project.Medecin

import kotlinx.android.synthetic.main.medecin_list_element.view.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent

import android.net.Uri
import MedecinAdapter.MyViewHolder
import com.medecin.project.R
import com.medecin.project.Util
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class Adapter_AjoutMedecinTraitant(private val myDataset: ArrayList<Medecin>) :
    RecyclerView.Adapter<Adapter_AjoutMedecinTraitant.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val medecinName = view?.findViewById(R.id.medecin_name) as TextView
        val medecinSpecialite = view?.findViewById(R.id.medecin_specialite) as TextView
        val medecinPhone = view?.findViewById(R.id.medecin_phone) as TextView
        val medecinCommune = view?.findViewById(R.id.medecin_commun) as TextView
        val medecinOpen = view?.findViewById(R.id.medecin_open_time) as TextView
        val medecinClose = view?.findViewById(R.id.medecin_close_time) as TextView
        val button = view?.findViewById(R.id.add_button) as ImageButton
        val preferences = view.getContext().getSharedPreferences("status",Context.MODE_PRIVATE)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Adapter_AjoutMedecinTraitant.MyViewHolder {
        // create a new view

        val listElement = LayoutInflater.from(parent.context)
            .inflate(com.medecin.project.R.layout.medecin_traitant_list_element, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(listElement)


    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.medecinClose.text = myDataset[position].heure_fermeture
        holder.medecinOpen.text = myDataset[position].heure_ouverture
        holder.medecinCommune.text = myDataset[position].commune
        holder.medecinSpecialite.text = myDataset[position].specialite
        holder.medecinName.text = myDataset[position].full_name
        holder.medecinPhone.text = myDataset[position].phone.toString()

        holder.button.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            Log.e("Date is",currentDate)
            val d = DemandeAjout(currentDate,"pending",holder.preferences.getString("nom","none")!!,holder.preferences.getString("phone","none")!!,myDataset[position].phone.toString())
            val call = RetrofitService.endpoint.ajouterMedecinTraitant(d)
            call.enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>?, response:
                Response<String>?) {
                    myDataset.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
                override fun onFailure(call: Call<String>, t: Throwable) {

                }
            })
            Log.e("Button pressed", "Button is pressed")
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}
