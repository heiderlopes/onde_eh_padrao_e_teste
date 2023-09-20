package br.com.heiderlopes.ondeeh.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.heiderlopes.ondeeh.R
import br.com.heiderlopes.ondeeh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.btPesquisar.setOnClickListener {
            mainViewModel.pesquisar(binding.etCep.text.toString())
        }

        registerObserver()
    }

    private fun registerObserver() {
        mainViewModel.enderecoResponse.observe(this, Observer{
            binding.tvBairro.text = it.bairro
            binding.tvLogradouro.text = it.logradouro
            binding.tvLocalidade.text = it.localidade
            binding.tvUF.text = it.uf
        })

        mainViewModel.errorResponse.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}
