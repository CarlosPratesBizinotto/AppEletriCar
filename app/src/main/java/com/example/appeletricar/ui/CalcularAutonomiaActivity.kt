package com.example.appeletricar.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.AsyncTaskLoader
import com.example.appeletricar.R
import java.lang.Exception
import java.net.HttpURLConnection

class CalcularAutonomiaActivity : AppCompatActivity() {
    lateinit var precokwh: EditText
    lateinit var kmpercorrido: EditText
    lateinit var btnCalcular: Button
    lateinit var resultado: TextView
    lateinit var btnClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListenrs()

    }
    //Recuperar os campos da Activity_calcular_autonomia
    fun setupView(){
        kmpercorrido = findViewById(R.id.et_km_percorrido)
        precokwh = findViewById(R.id.et_preco_kwh)
        resultado = findViewById(R.id.et_resultadokmpreco)
        btnCalcular = findViewById(R.id.btn_calcular)
        btnClose = findViewById(R.id.iv_close)
    }



    fun setupListenrs() {
        btnCalcular.setOnClickListener {
            calcular()
        }
        btnClose.setOnClickListener{
          finish()
        }
}

    fun calcular() {
        val preco = precokwh.text.toString().toFloat()
        val km = kmpercorrido.text.toString().toFloat()

        val result = preco / km

        resultado.text = result.toString()
        //Log.d("Texto digitado ->", preco.toString())
        //Log.d("Texto digitado ->", km.toString())
        //Log.d("Resultado Media ->", resultado.toString())
    }


}