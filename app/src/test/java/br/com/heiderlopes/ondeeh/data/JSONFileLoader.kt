package br.com.heiderlopes.ondeeh.data

import br.com.heiderlopes.ondeeh.model.Endereco
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.InputStreamReader


class JSONFileLoader {
    private var jsonStr: String? = null

    fun loadJSONString(file: String): String?{
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return jsonStr
    }

    fun loadAddress(file: String): Endereco?{
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()

        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Endereco> = moshi.adapter(Endereco::class.java)
        return jsonAdapter.fromJson(jsonStr)
    }
}
