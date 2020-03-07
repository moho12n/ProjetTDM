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

class MedecinTraitantActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private  var myDataset : ArrayList<Medecin> = ArrayList()
    //---- Drawer
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecin_traitant)

        ajouter_medecin_boutton.setOnClickListener {
            val intent = Intent(this, AjoutMedecinTraitant::class.java)
            startActivity(intent)
        }
        viewAdapter = MedecinTraitantAdapter(myDataset)
        viewManager = LinearLayoutManager(this)


        val pref = this.getSharedPreferences("status"

            , Context.MODE_PRIVATE)

        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                Log.e("element is ", tab.position.toString())
                var call = RetrofitService.endpoint.getMedTraitant(pref.getString("phone","none")!!,"pending")
                if (tab.position === 0 ) call = RetrofitService.endpoint.getMedTraitant(pref.getString("phone","none")!!,"accepted")
                else call = RetrofitService.endpoint.getMedTraitant(pref.getString("phone","none")!!,"pending")
                call.enqueue(object: Callback<List<Medecin>> {
                    override fun onResponse(call: Call<List<Medecin>>?, response:
                    Response<List<Medecin>>?) {
                        myDataset.clear()
                        myDataset.addAll(ArrayList( response?.body()!!))
                        Log.e("dataset",myDataset.toString())
                        viewAdapter.notifyDataSetChanged()
                    }
                    override fun onFailure(call: Call<List<Medecin>>, t: Throwable) {
                        Toast.makeText(this@MedecinTraitantActivity, t!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val call = RetrofitService.endpoint.getMedTraitant("782237885","pending")
        call.enqueue(object: Callback<List<Medecin>> {
            override fun onResponse(call: Call<List<Medecin>>?, response:
            Response<List<Medecin>>?) {
                myDataset.addAll(ArrayList( response?.body()!!))
                Log.e("dataset",myDataset.toString())
                viewAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<Medecin>>, t: Throwable) {
                Toast.makeText(this@MedecinTraitantActivity, t!!.message, Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView = findViewById<RecyclerView>(R.id.list_medecin_traitant).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
//------ Navigation Drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
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
                Toast.makeText(this, "MAin activity", Toast.LENGTH_SHORT).show()
            }
            R.id.rendez_vous_id -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.suivi_id -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.aide_id -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.deconnexion_id -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}

