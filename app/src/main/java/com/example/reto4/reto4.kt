package com.example.reto4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.api.HeaderInterceptor
import com.example.api.ParametersDto
import com.example.api.balamKnightsResponse
import com.example.api.balamKnightsService
import com.example.api.local_api
import com.example.reto4.databinding.Reto4Binding
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class reto4 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: Reto4Binding
    lateinit var user: EditText
    lateinit var pass: EditText
    private var buttonAccept: Button? = null

    var stringBuilderFinal: String? = null

    var imageurl: String? = null
    var name: String? = null
    var address: String? = null

    private var usr: String? = null
    private var passw: String? = null
    private var flagLogin: Boolean? = false

    private var BaseURL_Post = "http://www.balam-knights.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = Reto4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getLoginResult()

        user = binding.edTU
        pass = binding.edTP
        buttonAccept = binding.loginButton

        buttonAccept?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.loginButton -> {
                if (user.text.toString() != "" && pass.text.toString() != ""){

                    usr = user.text.toString()
                    passw = pass.text.toString()
                    flagLogin = true;

                    getLoginResult()
                }
                else
                    showToast("No contiene texto...")
            }
        }
    }

    private fun getLoginResult(){
         val retrofit = Retrofit.Builder()
            .baseUrl(BaseURL_Post)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()

        val service = retrofit.create(balamKnightsService::class.java)

        if(flagLogin == true) {
            val dtoParametersDto = ParametersDto(usr.toString(), passw.toString())

            val call = service.getResultLogin(dtoParametersDto)

            call.enqueue(object : retrofit2.Callback<balamKnightsResponse> {

                override fun onResponse(call: retrofit2.Call<balamKnightsResponse>, response: retrofit2.Response<balamKnightsResponse>) {

                    if (response.code() == 200) {
                        val balamKnightsResp = response.body()!!

                        imageurl = balamKnightsResp.imageurl!!
                        name = balamKnightsResp.name!!
                        address = balamKnightsResp.address!!
                        validaLogin()
                    }
                    else
                        showToast("Error: " + response.code() + "Credenciales incorrectas")
                }

                override fun onFailure(call: retrofit2.Call<balamKnightsResponse>, t: Throwable) {
                    stringBuilderFinal = "Whitout data"
                    showToast("Error en llamada a método" + t.message)
                }
            })
        }
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

    private fun validaLogin(){
        val sessionToken = local_api().validateUser("admin", "admin")

        if(sessionToken != null){
            val intent = Intent(this, reto4_menu::class.java).apply {
                if(imageurl != null && name != null && address != null){
                    putExtra("imageurl", imageurl)
                    putExtra("name", name)
                    putExtra("address", address)
                }
                else
                    putExtra("SESSION_TOKEN", sessionToken)
            }
            startActivity(intent)
        }else {
            showToast("Credenciales incorrectas...")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}