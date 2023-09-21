package br.com.heiderlopes.ondeeh.ui.main

import br.com.heiderlopes.ondeeh.model.Endereco

interface MainContract {

    interface MainView {
        fun mostraErro(mensagem: String)
        fun mostrarEndereco(endereco: Endereco?)
        fun mostrarLoading()

        fun esconderLoading()
    }

    interface MainPresenter {
        fun pesquisar(cep: String)
    }

}