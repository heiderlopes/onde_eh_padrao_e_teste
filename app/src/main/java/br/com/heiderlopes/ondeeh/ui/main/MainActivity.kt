package br.com.heiderlopes.ondeeh.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.heiderlopes.ondeeh.R
import br.com.heiderlopes.ondeeh.databinding.ActivityMainBinding
import br.com.heiderlopes.ondeeh.model.Endereco

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mainPresenter = MainPresenter(this)
        setUpListener()
    }

    private fun setUpListener() {
        binding.btPesquisar.setOnClickListener {
            mainPresenter.pesquisar(binding.etCep.text.toString())
        }
    }

    override fun mostraErro(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    override fun mostrarEndereco(endereco: Endereco?) {
        binding.tvBairro.text = endereco?.bairro
        binding.tvLocalidade.text = endereco?.localidade
        binding.tvLogradouro.text = endereco?.logradouro
        binding.tvUF.text = endereco?.uf
    }

    override fun mostrarLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun esconderLoading() {
        binding.loading.visibility = View.GONE
    }
}