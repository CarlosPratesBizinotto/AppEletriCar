package com.example.appeletricar.data

import com.example.appeletricar.dominio.Carro

//Pode ser class ou Object
//Se colocar como Classe tem que estanciar exemplo: CarFactory()
//Se colocar object pode ir direto

object CarFactory {

    val list = listOf<Carro>(
        Carro(
            id = 1,
            nome = "Carro_da_Andrea",
            preco = "R$ 400.000,00",
            bateria = "900 kwh",
            potencia = "200cv",
            recarga = "10 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false

        ),
        Carro(
            id = 2,
            nome = "Mercedes_Teste",
            preco = "R$ 500.000,00",
            bateria = "1900 kwh",
            potencia = "2200cv",
            recarga = "12 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false
        ),

        Carro(
            id = 3,
            nome = "HondaFit",
            preco = "R$ 100.000,00",
            bateria = "2900 kwh",
            potencia = "220cv",
            recarga = "9 min",
            urlPhoto = "https://maximatech.com.br/wp-content/uploads/2019/04/logo-maximatech-e1554815418600-300x76.png",
            isFavorite = false
        )
    )
    //VERBOS HTTP
      // - GET -> Para recuperar informações
     // - POST -> Para enviar informações para um servidor
     // - DELETE -> Para deletar algum recurso
     // - PUT -> Alterar uma entidade como um todo (Exemplo carro, se usar esse verbo altera todas as inforamçoes)
     // - PATCH -> Alterar um atributo da entidade
}