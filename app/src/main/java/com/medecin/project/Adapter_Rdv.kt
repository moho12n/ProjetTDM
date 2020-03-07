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


class RendezVousAdapter(private val myDataset: ArrayList<RendezVous>) :
    RecyclerView.Adapter<RendezVousAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val medecinName = view?.findViewById(R.id.nom_medecin_rdv) as TextView
        val dateRdv = view?.findViewById(R.id.date_rdv) as TextView

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RendezVousAdapter.MyViewHolder {
        // create a new view
        val listElement = LayoutInflater.from(parent.context)
            .inflate(com.medecin.project.R.layout.rendez_vous_list_element, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(listElement)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.medecinName.text = myDataset[position].medecin
        holder.dateRdv.text = myDataset[position].date

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
