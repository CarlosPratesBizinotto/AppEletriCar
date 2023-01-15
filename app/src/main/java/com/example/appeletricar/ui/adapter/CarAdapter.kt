package com.example.appeletricar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appeletricar.R
import com.example.appeletricar.dominio.Carro

//CarAdapter recebe uma lista de carros definita no data
class CarAdapter(private val carros: List<Carro>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>(){

    var carItemLister : (Carro) -> Unit = {}

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
        //holder.urlPhoto.setImageState()
        holder.favorito.setOnClickListener{
            val carro = carros[position]
             carItemLister(carro)
            setupFavorite(carro, holder)
        }


    }

    private fun setupFavorite(
        carro: Carro,
        holder: ViewHolder
    ) {
        carro.isFavorite = !carro.isFavorite

        if (carro.isFavorite)
            holder.favorito.setImageResource(R.drawable.ic_star_selected)
        else
            holder.favorito.setImageResource(R.drawable.ic_star)
    }

    //Quantidade de itens da lista
    override fun getItemCount(): Int = carros.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nome: TextView
        val preco: TextView
        val bateria: TextView
        val potencia: TextView
        val recarga: TextView
        val favorito: ImageView
        //val urlPhoto: ImageView

        // A palavra (((Apply))) recuso para otimizar e nao precisar ficar repetindo a palavra view no caso do exemplo
        init {
            view.apply {
                nome = findViewById(R.id.tv_nome_car)
                preco = findViewById(R.id.tv_preco_value)
                bateria = findViewById(R.id.tv_carga_bateria)
                potencia = findViewById(R.id.tv_potencia_cavalos)
                recarga = findViewById(R.id.tv_tempo_recarga)
                favorito = findViewById(R.id.iv_favorite)
                //urlPhoto = findViewById(R.id.iv_img_car)
            }
        }
    }
}