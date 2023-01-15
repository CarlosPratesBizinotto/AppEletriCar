package com.example.appeletricar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appeletricar.R
import com.example.appeletricar.dominio.Carro

//CarAdapter recebe uma lista de carros definita no data
class CarAdapter(private val carros: List<Carro>) : RecyclerView.Adapter<CarAdapter.ViewHolder>(){

//Recuperar o Layout que foi criado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent,false)
        return ViewHolder(view)
    }

    //Pega o conteudo da view e troca pela informação de um item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nome.text = carros[position].nome
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
    }

    //Quantidade de itens da lista
    override fun getItemCount(): Int = carros.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nome: TextView
        val preco: TextView
        val bateria: TextView
        val potencia: TextView
        val recarga: TextView

        //Apply recuso para otimizar e nao precisar ficar repetindo a palavra view no caso do exemplo
        init {
            view.apply {
                nome = findViewById(R.id.tv_nome_car)
                preco = findViewById(R.id.tv_preco_value)
                bateria = findViewById(R.id.tv_carga_bateria)
                potencia = findViewById(R.id.tv_potencia_cavalos)
                recarga = findViewById(R.id.tv_tempo_recarga)
            }
        }
    }
}