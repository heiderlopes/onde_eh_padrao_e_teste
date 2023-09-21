package br.com.heiderlopes.ondeeh.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.heiderlopes.ondeeh.data.EnderecoRepository
import br.com.heiderlopes.ondeeh.data.remote.APIService
import br.com.heiderlopes.ondeeh.model.Endereco
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val result = MutableLiveData<Endereco>()

    fun getResult(): LiveData<Endereco> = result

    private val repository: EnderecoRepository by lazy {
        EnderecoRepository(APIService.instance!!)
    }

    suspend fun pesquisar(cep: String){
        viewModelScope.launch {
            val resultServer = repository.getEndereco(cep)
            result.value = resultServer
        }
    }
}
