package com.medecin.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_medecin_traitant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AjouterRdvActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
//------
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private  var myDataset : ArrayList<RendezVous> = ArrayList()
//-------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_rdv)




        val pref = this.getSharedPreferences("status"

            , Context.MODE_PRIVATE)
        viewAdapter = RendezVousAdapter(myDataset)
        viewManager = LinearLayoutManager(this)

        val call = RetrofitService.endpoint.getRdv(pref.getString("phone","none")!!)
        call.enqueue(object: Callback<List<RendezVous>> {
            override fun onResponse(call: Call<List<RendezVous>>?, response:
            Response<List<RendezVous>>?) {
                myDataset.addAll(ArrayList( response?.body()!!))
                Log.e("dataset",myDataset.toString())
                viewAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<RendezVous>>, t: Throwable) {
                Toast.makeText(this@AjouterRdvActivity, t!!.message, Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView = findViewById<RecyclerView>(R.id.list_rdv).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_medecins_recherche ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "MAin activity", Toast.LENGTH_SHORT).show()
            }
            R.id.list_medecin_traitant_id -> {
                val intent = Intent(this, MedecinTraitantActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "List Medecin Traitant", Toast.LENGTH_SHORT).show()
            }
            R.id.rendez_vous_id -> {
                val intentrdv = Intent(this, AjouterRdvActivity::class.java)
                startActivity(intentrdv)
                Toast.makeText(this, "Rendez Vous", Toast.LENGTH_SHORT).show()
            }
            R.id.suivi_id -> {
                Toast.makeText(this, "Suivi", Toast.LENGTH_SHORT).show()
            }
            R.id.aide_id -> {
                Toast.makeText(this, "Aide", Toast.LENGTH_SHORT).show()
            }
            R.id.deconnexion_id -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}