package com.example.captiveportal

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.captiveportal.constants.Constants
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {
    private var net: Network? = null
    private var captivePortal: CaptivePortal? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ConnectivityManager.ACTION_CAPTIVE_PORTAL_SIGN_IN == intent.action) {
            net = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK)
            captivePortal = intent.getParcelableExtra(ConnectivityManager.EXTRA_CAPTIVE_PORTAL)
        }

        btn_plan_a.setOnClickListener {
            makePost()
        }
    }

    private fun makePost(){
        doAsync {
            if (net!=null && captivePortal != null) {
                val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                cm.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .build(),
                    object : NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cm.bindProcessToNetwork(net)
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ConnectivityManager.setProcessDefaultNetwork(network)
                            }
                        }
                    })

                val clientForData = OkHttpClient()

                val urlForData = HttpUrl.Builder()
                    .scheme("https")
                    .host(Constants.CAPTIVE_PORTAL_URL)
                    .addPathSegment("login")
                    .build()

                val requestForData = Request.Builder()
                    .url(urlForData)
                    .build()

                clientForData.newCall(requestForData).execute().use {
                    val body = it.body()!!.string()
                    val headers = it.headers()
                    val doc = Jsoup.parse(body)
                    if (!it.isSuccessful) {
                        Log.d("ERROR1", "error: ${it.message()}")
                    } else {
                        Log.d("EXITO1", "mensaje: ${it.message()}")
                        val client = OkHttpClient()

                        val url = HttpUrl.Builder()
                            .scheme("https")
                            .host(Constants.CAPTIVE_PORTAL_URL)
                            .addPathSegment("login")
                            /*.addQueryParameter("username", "BazEktWFauto")
                            .addQueryParameter("password", "Phtx-N4vq.Fkp02")*/
                            .addQueryParameter("username","jhghfdhjk")
                            .addQueryParameter("password","thfgjyhkuijlkoñ")
                            .build()

                        val request = Request.Builder()
                            .url(url)
                            .build()

                        client.newCall(request).execute().use {resp ->
                            if (!resp.isSuccessful) {
                                Log.d("ERROR1", "error: ${resp.message()}")
                            } else {
                                Log.d("EXITO1", "mensaje: ${resp.message()}")
                            }
                        }
                    }
                }
            }

            uiThread {

            }
        }
    }
}
