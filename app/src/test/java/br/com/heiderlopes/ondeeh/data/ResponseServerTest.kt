package br.com.heiderlopes.ondeeh.data

import br.com.heiderlopes.ondeeh.model.Endereco
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class ResponseServerTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }


    @Test
    fun `le o arquivo json com sucesso`(){
        val reader = JSONFileLoader().loadJSONString("address_response_success.json")
        assertThat(reader, `is`(notNullValue()))
        assertThat(reader, containsString("Praça da Sé"))
    }
    @Test
    fun `busca endereco e verifica se o logradouro existe`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("address_response_success.json")
                ?: "{errorCode:34}")
        mockWebServer.enqueue(response)

        assertThat(response.getBody()?.readUtf8(), containsString("\"logradouro\""))
    }

    @Test
    fun `busca o endereco e verifica se o logradouro contem Praça da Sé`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("address_response_success.json")
                ?: "{errorCode:34}")

        mockWebServer.enqueue(response)

        assertThat(response.getBody()?.readUtf8(), containsString("logradouro"))

        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Endereco> = moshi.adapter(Endereco::class.java)
        val json = jsonAdapter.fromJson(response.getBody()?.readUtf8() ?: "")

        assertThat(json.logradouro, `is`("Praça da Sé"))
    }
}
