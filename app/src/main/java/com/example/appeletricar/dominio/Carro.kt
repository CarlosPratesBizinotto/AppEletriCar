package com.example.appeletricar.dominio

import android.widget.ImageView
import org.json.JSONObject

//Abstrai alguns gets / sets, construtores, toString
data class Carro (
    val id: Int,
    val nome: String,
    val preco: String,
    val bateria: String,
    val potencia: String,
    val recarga: String,
    val urlPhoto: String,
    var isFavorite: Boolean
    )

