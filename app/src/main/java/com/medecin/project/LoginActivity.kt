package com.medecin.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btn_new_activity = findViewById(R.id.ButtonLoginToSignUp) as Button
        btn_new_activity.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        var btnlogin = findViewById(R.id.btnchangepassword) as Button
        btnlogin.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            val intent1 = Intent(this, Changepass::class.java)



            val call = RetrofitService.endpoint.login( EditTextTelephoneLogin.text.toString(),EditTextTelephonePassword.text.toString())
            call.enqueue(object: Callback<List<Patient>> {
                override fun onResponse(call: Call<List<Patient>>?, response:
                Response<List<Patient>>?) {
                    if(response?.body().isNullOrEmpty()){
                        Toast.makeText(this@LoginActivity, "Veuillez verifier vos information", Toast.LENGTH_LONG).show()
                    }
                    else {
                        if (response?.body()!![0].newpassword=="0"){
                            log(response?.body()!![0].phone,response?.body()!![0].password,response?.body()!![0].Nom)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@LoginActivity, "Accueil", Toast.LENGTH_SHORT).show()
                            intent1.putExtra("phone",response?.body()!![0].phone)
                            startActivity(intent1)
                            log(response?.body()!![0].phone,response?.body()!![0].password,response?.body()!![0].Nom)
                        }
                    }

                }
                override fun onFailure(call: Call<List<Patient>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t!!.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }


fun check():Boolean{


    val pref = this.getSharedPreferences("status"

        ,Context.MODE_PRIVATE)

    val con = pref.getBoolean("connected"

        , false)
    println(con.toString())
    return con

}
 fun log(phone :String,password:String,nom:String){
     val pref = this.getSharedPreferences("status"

         ,Context.MODE_PRIVATE)
     pref.edit {
         putBoolean("connected"
             ,true)
         putString("phone",phone)
         putString("password",password)
         putString("nom",nom)


     }

 }



}
