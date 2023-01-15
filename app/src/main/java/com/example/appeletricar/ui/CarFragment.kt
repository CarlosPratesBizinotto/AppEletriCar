package com.example.appeletricar.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appeletricar.R
import com.example.appeletricar.data.CarFactory
import com.example.appeletricar.data.CarsApi
import com.example.appeletricar.data.local.CarrosContract
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_FOTOS
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_NOME
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.appeletricar.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.appeletricar.data.local.CarrosContract.CarEntry.TABLE_NAME
import com.example.appeletricar.data.local.CarsDbHelper
import com.example.appeletricar.dominio.Carro
import com.example.appeletricar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {

    lateinit var fabCalcular : FloatingActionButton
    lateinit var listaCarros: RecyclerView
    lateinit var progress : ProgressBar
    lateinit var noInternetImage : ImageView
    lateinit var noInternetText : TextView
    lateinit var carsApi: CarsApi

    var carrosArray : ArrayList<Carro> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListenrs()

    }

    override fun onResume() {
        super.onResume()
        if(checkForInternet(context)){
            //callServices() -> Outra forma de chamar serviços
            getAllCars()
        } else {
            emptyState()
        }
    }


    //Usando Retrofit para fazer ligação com o JSON
    fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/CarlosPratesBizinotto/api-EletriCars/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)

    }

    fun getAllCars(){
        carsApi.getALLCars().enqueue(object : Callback<List<Carro>> {
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if(response.isSuccessful){
                    progress.visibility = View.GONE
                    noInternetImage.visibility = View.GONE
                    noInternetText.visibility = View.GONE

                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context,R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context,R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

//Pode usar visibility = View.GONE/VISIBLE ou isVisible = false/true
    fun emptyState(){
        progress.isVisible = false
        listaCarros.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }

    fun setupView(view: View) {
        view.apply {
            fabCalcular = findViewById(R.id.fab_calcular)
            listaCarros = findViewById(R.id.rv_lista_carros)
            progress = findViewById(R.id.pb_loader)
            noInternetImage = findViewById(R.id.iv_empty_state)
            noInternetText = findViewById(R.id.tv_no_wifi)
        }

    }

    fun setupList(lista: List<Carro>) {
        val carroAdapter = CarAdapter(lista)
        listaCarros.apply {
            visibility = View.VISIBLE
            adapter = carroAdapter

        }

        carroAdapter.carItemLister = { carro ->
            val bateria = carro.bateria
        }

    }

    fun setupListenrs() {

        fabCalcular.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun callServices(){
        val urlBase = "https://raw.githubusercontent.com/CarlosPratesBizinotto/api-EletriCars/main/eletricar.json"
        MyTask().execute(urlBase)

    }


    //Validação de conexão com a internet
    fun checkForInternet(Contexto : Context?) : Boolean{
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            val network = connectivityManager.activeNetwork ?: return false

            val activityNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }

    }
    //Utilizar o retrofit como abstração do AsyncTask
    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("Mytask","Iniciando...")
            progress.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection : HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                   "application/json"
                )

                val responseCode = urlConnection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK){
                    var response = urlConnection.inputStream.bufferedReader().use{it.readText()}
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponivel no momento...")
                }

            } catch (ex: Exception){
               Log.e("Erro", "Erro ao realizar processamento....")
            } finally {
                urlConnection?.disconnect()
            }
            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try{
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for(i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val nome = jsonArray.getJSONObject(i).getString("nome")
                    Log.d("Nome ->", nome)

                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("Preco ->", preco)

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("Bateria ->", bateria)

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("Potencia ->", potencia)

                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    Log.d("Recarga ->", recarga)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("UrlPhoto ->", urlPhoto)

                    val model = Carro(
                        id = id.toInt(),
                        nome = nome,
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto,
                        isFavorite = false
                    )
                    carrosArray.add(model)
                    Log.d("Model ->", model.toString())
                }
                progress.visibility = View.GONE
                noInternetImage.visibility = View.GONE
                noInternetText.visibility = View.GONE
                //setupList()
            } catch (ex: Exception){
              Log.e("Erro ->", ex.message.toString())
            }
        }

    }

}





