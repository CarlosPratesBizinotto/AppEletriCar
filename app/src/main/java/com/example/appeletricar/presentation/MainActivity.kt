package com.example.appeletricar.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.appeletricar.R

class MainActivity : AppCompatActivity() {
    lateinit var radioGroup: RadioGroup
    lateinit var Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListenrs()
    }

    //Recuperar os campos da ActivityMain
    fun setupView() {
        radioGroup = findViewById(R.id.rg_group_colors)
        Button = findViewById<Button>(R.id.btn_calular_consumo)
    }

    fun setupListenrs(){

        radioGroup.setOnCheckedChangeListener { _, checkedId: Int ->
            val radio = findViewById<RadioButton>(checkedId)
            Log.d("Opção Selecionada ->", radio.text.toString())
        }

        Button.setOnClickListener{
              startActivity(Intent( this, CalcularAutonomiaActivity::class.java))
        }


    }


}