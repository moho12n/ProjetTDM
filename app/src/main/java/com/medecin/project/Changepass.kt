package com.medecin.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_changepass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Changepass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepass)
        val ss:String = intent.getStringExtra("phone")
        val btn = findViewById(R.id.btnchangepassword) as Button
        btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val call = RetrofitService.endpoint.update(ss,EditTextTelephonePassword1.text.toString())
            call.enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response?.isSuccessful!!){
                        val str:String? = response.body()
                        Toast.makeText(
                            this@Changepass,
                            "your password is updated",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)


                    }
                    else
                    {
                        Toast.makeText(
                            this@Changepass,
                            "err",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@Changepass, "errreur", Toast.LENGTH_SHORT).show()
                }
            })


        }

    }

    fun update(){



    }
}
