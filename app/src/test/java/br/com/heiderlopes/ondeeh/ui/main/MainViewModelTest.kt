package br.com.heiderlopes.ondeeh.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.heiderlopes.ondeeh.utils.MainCoroutineRule
import br.com.heiderlopes.ondeeh.data.remote.EnderecoService
import br.com.heiderlopes.ondeeh.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()


    private lateinit var mainViewModel: MainViewModel
    private lateinit var service: EnderecoService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
            retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup() {
        mainViewModel = MainViewModel()
        service = retrofit.create(EnderecoService::class.java)
    }

    @Test
    fun `verifica se o endereco nao é nulo`() {
        runBlocking {
            val result = service.pesquisar("01001000")
            assertThat(result, Matchers.`is`(Matchers.notNullValue()))
        }
    }

    @Test
    fun  `verifica se o logradouro eh Praça da Sé`() {
        runBlocking {
            val result = service.pesquisar("01001000")
            assertThat(result.logradouro, Matchers.`is`("Praça da Sé"))
        }
    }


    @Test
    fun `verifica se o response de erro quando o cep não for encontrado`(){
        runBlocking {
            try{
                val result = service.pesquisar("0")
            }catch (e: java.lang.Exception) {
                assertThat(e.localizedMessage, Matchers.`is`("HTTP 400 Bad Request"))
            }
        }
    }

    @Test
    fun `verifica se o logradouro é Praça da Sé`() {
        runBlocking {
            mainViewModel.pesquisar("01001000")
            delay(3000)
            val result = mainViewModel.getResult().getOrAwaitValue()
            assertThat(result.logradouro, `is`("Praça da Sé"))
        }
    }
}

