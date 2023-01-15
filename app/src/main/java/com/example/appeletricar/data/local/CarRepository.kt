package com.example.appeletricar.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.appeletricar.dominio.Carro
import java.lang.Exception

class CarRepository {

    //METODO SOBRE BANCO DE DADOS
    fun save(context: Context, carro: Carro): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(CarrosContract.CarEntry.COLUMN_NAME_NOME, carro.nome)
                put(CarrosContract.CarEntry.COLUMN_NAME_PRECO, carro.preco)
                put(CarrosContract.CarEntry.COLUMN_NAME_BATERIA, carro.bateria)
                put(CarrosContract.CarEntry.COLUMN_NAME_POTENCIA, carro.potencia)
                put(CarrosContract.CarEntry.COLUMN_NAME_RECARGA, carro.recarga)
                put(CarrosContract.CarEntry.COLUMN_NAME_FOTOS, carro.urlPhoto)
            }


            val inserted = db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)
            if (inserted != null){
                isSaved = true
            }

        } catch (ex: Exception){
            ex.message?.let{
                Log.e("Erro ao inserir -> ", it)
            }
        }
        return isSaved
    }
}