package br.com.heiderlopes.ondeeh.ui.main

import br.com.heiderlopes.ondeeh.data.remote.APIService
import br.com.heiderlopes.ondeeh.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var mainView: MainContract.MainView) : MainContract.MainPresenter {
    override fun pesquisar(cep: String) {
        if(cep.isEmpty()) {
            mainView.mostraErro("Informe o CEP a ser pesquisado")
        } else {
            APIService.instance
                ?.pesquisar(cep)
                ?.enqueue(object : Callback<Endereco> {
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        if(response.isSuccessful) {
                            mainView.mostrarEndereco(response.body())
                        } else {
                            mainView.mostraErro("Endereco não encontrado")
                        }
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        mainView.mostraErro(t.message.toString())
                    }
                })
        }

    }
}
