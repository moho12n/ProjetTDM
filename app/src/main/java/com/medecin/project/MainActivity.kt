package com.medecin.project

import MedecinAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private  var myDataset : ArrayList<Medecin> = ArrayList()
//---------------------
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    var mContext:Context = this@MainActivity

    public fun MainActivity(context: Context) {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewAdapter = MedecinAdapter(myDataset)
        viewManager = LinearLayoutManager(this)

        val pref = this.getSharedPreferences("status"

            ,Context.MODE_PRIVATE)
        //if(pref.getString("nom","none") != null) nom_patient_id.text = pref.getString("nom","none")
        val call = RetrofitService.endpoint.getmedecin()
        call.enqueue(object: Callback<List<Medecin>> {
            override fun onResponse(call: Call<List<Medecin>>?, response:
            Response<List<Medecin>>?) {
                myDataset.addAll(ArrayList( response?.body()!!))
                Log.e("dataset",myDataset.toString())
                viewAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<Medecin>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t!!.message, Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView = findViewById<RecyclerView>(R.id.list_id).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        EditTextRechercheCommune.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val call2 = RetrofitService.endpoint.getMedecinSpecAndComm(EditTextRechercheCommune.text.toString(),EditTextRechercheSpecialite.text.toString())
                    call2.enqueue(object: Callback<List<Medecin>> {
                        override fun onResponse(call2: Call<List<Medecin>>?, response:
                        Response<List<Medecin>>?) {
                            myDataset.clear()
                            if(response?.body() != null) myDataset.addAll(ArrayList( response?.body()!!))
                            Log.e("dataset",myDataset.toString())
                            viewAdapter.notifyDataSetChanged()
                        }
                        override fun onFailure(call: Call<List<Medecin>>, t: Throwable) {
                            Toast.makeText(this@MainActivity, t!!.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }


        })

        EditTextRechercheSpecialite.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val call2 = RetrofitService.endpoint.getMedecinSpecAndComm(EditTextRechercheCommune.text.toString(),EditTextRechercheSpecialite.text.toString())
                call2.enqueue(object: Callback<List<Medecin>> {
                    override fun onResponse(call2: Call<List<Medecin>>?, response:
                    Response<List<Medecin>>?) {
                        myDataset.clear()
                        if(response?.body() != null) myDataset.addAll(ArrayList( response?.body()!!))
                        Log.e("dataset",myDataset.toString())
                        viewAdapter.notifyDataSetChanged()
                    }
                    override fun onFailure(call: Call<List<Medecin>>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }


        })

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
    fun logout(){
        val pref = this.getSharedPreferences("status"

            ,Context.MODE_PRIVATE)
        pref.edit {
            putBoolean("connected"
                ,false)




        }

    }

    fun check():Boolean{

        val pref = this.getSharedPreferences("status"

            ,Context.MODE_PRIVATE)

        val con = pref.getBoolean("connected"

            , false)
        return con

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_medecins_recherche ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "MAin activity", Toast.LENGTH_SHORT).show()
            }
            R.id.list_medecin_traitant_id -> {


                val con = check()


                if (con) {
                    val intent = Intent(this, MedecinTraitantActivity::class.java)
                    // To pass any data to next activity
                    //intent.putExtra("keyIdentifier", value)
                    // start your next activity
                    startActivity(intent)
                }
                else {Toast.makeText(this, "You have to login ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }
                 }
            R.id.rendez_vous_id -> {
                val con = check()


                if (con) {
                    val intentrdv = Intent(this, AjouterRdvActivity::class.java)
                    startActivity(intentrdv)
                }
                else {Toast.makeText(this, "You have to login ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }            }
            R.id.suivi_id -> {
                val con = check()


                if (con) {

                }
                else {Toast.makeText(this, "You have to login ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }            }
            R.id.aide_id -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.deconnexion_id -> {
                logout()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}

