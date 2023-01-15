package com.example.appeletricar.data.local

import android.provider.BaseColumns

object CarrosContract {


    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_NOME = "nome"
        const val COLUMN_NAME_PRECO = "preco"
        const val COLUMN_NAME_BATERIA = "bateria"
        const val COLUMN_NAME_POTENCIA = "potencia"
        const val COLUMN_NAME_RECARGA = "recarga"
        const val COLUMN_NAME_FOTOS = "urlPhoto"
        const val COLUMN_NAME_FAVORITO = "favorito"
    }

    const val TABLE_CAR =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_NOME} TEXT," +
                "${CarEntry.COLUMN_NAME_PRECO} TEXT," +
                "${CarEntry.COLUMN_NAME_BATERIA} TEXT," +
                "${CarEntry.COLUMN_NAME_POTENCIA} TEXT," +
                "${CarEntry.COLUMN_NAME_RECARGA} TEXT," +
                "${CarEntry.COLUMN_NAME_FOTOS} TEXT," +
                "${CarEntry.COLUMN_NAME_FAVORITO} TEXT)"

   const val SQL_DELETE_ENTRIES =
       "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"

}