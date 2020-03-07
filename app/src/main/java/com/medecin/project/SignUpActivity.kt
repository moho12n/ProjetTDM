package com.medecin.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

    val btnsignup = findViewById(R.id.btnsignup) as Button
        btnsignup.setOnClickListener {
            val p =  Patient(
                EditTextNssSignUp.text.toString().toInt(),
                EditTextNomSignUp.text.toString(),
                EditTextPrenomSignUp.text.toString(),
                EditTextAdressSignUp.text.toString(),
                generateRandomPassword(),
                "1",
                EditTextPhoneSignUp.text.toString()
            )
            insererpatient(p)

            sendSMS(p)

        }

    }

    private  fun sendSMS(patient : Patient){
        val intent = Intent(this, PopUpSignup::class.java)

        val call = RetrofitService.endpoint.envoyerSMS(patient)


        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response?.isSuccessful!!){
                    val str:String? = response.body()

                    intent.putExtra("popuptitle", "Success")
                    intent.putExtra("popuptext", "Please check your phone for the sms containing your password!")
                    intent.putExtra("popupbtn", "OK")
                    intent.putExtra("darkstatusbar", false)
                    startActivity(intent)

                }
                else
                {
                    Toast.makeText(
                        this@SignUpActivity,
                        "err",
                        Toast.LENGTH_SHORT
                    ).show()

                    intent.putExtra("popuptitle", "Error")
                    intent.putExtra("popuptext", "Sorry, that email address is already used!")
                    intent.putExtra("popupbtn", "OK")
                    intent.putExtra("darkstatusbar", false)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "errreur",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..15) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return passWord
    }
    fun insererpatient(patient: Patient){
        val call = RetrofitService.endpoint.addpatient(patient)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response?.isSuccessful!!){
                    val str:String? = response.body()
                    Toast.makeText(
                        this@SignUpActivity,
                        str,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t!!.message, Toast.LENGTH_SHORT).show()
                println(t!!.message)
            }
        })

    }
}
