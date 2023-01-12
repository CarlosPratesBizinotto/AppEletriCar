package com.example.appeletricar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var precokwh: EditText
    lateinit var kmpercorrido: EditText
    lateinit var btnCalcular: Button
    lateinit var radioGroup: RadioGroup
    lateinit var resultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListenrs()
    }

    //Recuperar os campos da ActivityMain
    fun setupView() {
        kmpercorrido = findViewById(R.id.et_km_percorrido)
        precokwh = findViewById(R.id.et_preco_kwh)
        resultado = findViewById(R.id.et_resultadokmpreco)
        btnCalcular = findViewById(R.id.btn_calcular)
        radioGroup = findViewById(R.id.rg_group_colors)
    }

    fun setupListenrs() {
        btnCalcular.setOnClickListener {
            calcular()
        }


        radioGroup.setOnCheckedChangeListener { _, checkedId: Int ->
            val radio = findViewById<RadioButton>(checkedId)
            Log.d("Opção Selecionada ->", radio.text.toString())
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