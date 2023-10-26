package com.example.practic221

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Header
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.practic221.databinding.ActivityMainBinding
import org.json.JSONObject
import java.nio.charset.Charset

private val key:String = "e8tMZlKPTYXXHOIlj3lQxreacFnTte6O"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrency("eur","rub",10)
    }
    private fun getCurrency(from:String,to:String,amount:Int) {
        val url = "https://api.apilayer.com/fixer/convert?to=${to}&from=${from}&amount=${amount}"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Request.Method.GET, url,
            Response.Listener{ response ->

                Log.d("responseResult", "Response is: ${JSONObject(response).getString("result")}")
            },
            Response.ErrorListener {
                Log.d("responseResult","error")
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["apikey"] = key
                return headers
            }
        }
        queue.add(stringRequest)
    }

}