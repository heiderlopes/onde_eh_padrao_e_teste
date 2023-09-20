package br.com.heiderlopes.ondeeh.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.heiderlopes.ondeeh.data.remote.APIService
import br.com.heiderlopes.ondeeh.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val enderecoResponse = MutableLiveData<Endereco>()
    val errorResponse = MutableLiveData<String>()
    val loadingResponse = MutableLiveData<Boolean>()

    fun pesquisar(cep: String) {

        loadingResponse.value = true
        APIService.instance
            ?.pesquisar(cep)
            ?.enqueue(object : Callback<Endereco> {
                override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                    if(response.isSuccessful) {
                        val endereco = response.body()
                        if(endereco?.logradouro.equals(null)) {
                            errorResponse.value = "Endereço não encontrado"
                            enderecoResponse.value = Endereco("N/A", "N/A", "N/A", "N/A", "N/A", "N/A")
                        } else {
                            enderecoResponse.value = response.body()
                        }
                        loadingResponse.value = false
                    } else {
                        errorResponse.value = "Endereço não encontrado"
                        enderecoResponse.value = Endereco("N/A", "N/A", "N/A", "N/A", "N/A", "N/A")
                        loadingResponse.value = false
                    }
                }

                override fun onFailure(call: Call<Endereco>, t: Throwable) {
                    errorResponse.value = t.message.toString()
                    enderecoResponse.value = Endereco("N/A", "N/A", "N/A", "N/A", "N/A", "N/A")
                    loadingResponse.value = false
                }
            })

    }

}