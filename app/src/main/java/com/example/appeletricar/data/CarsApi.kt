package com.example.appeletricar.data

import com.example.appeletricar.dominio.Carro
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {


    @GET("eletricar.json")
    fun getALLCars(): Call<List<Carro>>

}