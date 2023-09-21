package br.com.heiderlopes.ondeeh.data

import br.com.heiderlopes.ondeeh.data.remote.EnderecoService
import br.com.heiderlopes.ondeeh.model.Endereco


class EnderecoRepository(private val service: EnderecoService) {
    suspend fun getEndereco(cep: String) : Endereco {
        return service.pesquisar(cep)
    }
}
