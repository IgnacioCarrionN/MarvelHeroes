package dev.carrion.marvelheroes.data.network

import android.util.Log
import dev.carrion.marvelheroes.md5
import dev.carrion.marvelheroes.models.CharacterDataWrapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Top level function to fetch characters on the remote source.
 * @property api MarvelApi instance.
 * @property name Name for the character we want to search.
 * @property initial Number offset for the Marvel API.
 * @property itemsPerPage Limit number for the Marvel API.
 * @property onSuccess Function called when the response from the remote source is successful.
 * @property onError Function called when the response from the remote source is an error.
 */
fun fetchCharacters(api: MarvelApi,
                    name: String?,
                    initial: Int,
                    itemsPerPage: Int,
                    onSuccess: (data: CharacterDataWrapper) -> Unit,
                    onError: (error: String) -> Unit
){

    Log.d("MarvelApi", "Initial: $initial, itemsPerPage: $itemsPerPage")

    val ts = System.currentTimeMillis()
    val hash = (ts.toString() + MarvelApi.PRIVATE_KEY + MarvelApi.API_KEY).md5()

    api.fetchCharacters(name = name, offset = initial, limit = itemsPerPage, ts = ts, hash = hash).enqueue(
            object : Callback<CharacterDataWrapper> {
                override fun onFailure(call: Call<CharacterDataWrapper>?, t: Throwable) {
                    Log.d("MarvelAPI", t.toString())
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                    call: Call<CharacterDataWrapper>?,
                    response: Response<CharacterDataWrapper>
                ) {
                    Log.d("MarvelAPI", "got a response $response")
                    if (response.isSuccessful) {
                        val characters = response.body()
                        Log.d("MarvelApi", "Response Successful: ${characters?.data?.count}")
                        characters?.let(onSuccess)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )
}

/**
 * Interface to declare the api calls.
 */
interface MarvelApi {

    @GET("/v1/public/characters")
    fun fetchCharacters(
        @Query("nameStartsWith") name: String? = null,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50,
        @Query("apikey") apyKey: String = API_KEY,
        @Query("ts") ts: Long,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>


    companion object {
        private const val BASE_URL = "https://gateway.marvel.com:443"
        const val API_KEY = "c8b6726d809730fa88a136229ebec54e"
        const val PRIVATE_KEY = "4677b77f88c31da6db69b41345b61aba3246a92a"

        /**
         * Create a MarvelApi instance.
         * @return MarvelApi instance.
         */
        fun create(): MarvelApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .writeTimeout(10L, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelApi::class.java)
        }
    }
}