package com.example.appeletricar.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appeletricar.R

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
        setupCachedResuld()

    }

    private fun setupCachedResuld() {
        val valorCalculado = getSharedPref()
        resultado.text = valorCalculado.toString()
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
        saveSharedPref(result)

        //Log.d("Texto digitado ->", preco.toString())
        //Log.d("Texto digitado ->", km.toString())
        //Log.d("Resultado Media ->", resultado.toString())
    }

    //Funcao que salva o resultado do Calcular
    fun saveSharedPref(resultado: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putFloat(getString(R.string.saved_calc), resultado)
            apply()
        }

    }
    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }

}