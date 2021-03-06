package nl.rvbsoftdev.curiosityreporting.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/** Retrofit NASA API service with Moshi JSON converter **/

private const val BASE_URL = "https://api.nasa.gov/"

interface NasaWebAPI {

    /** no CallBacks since using Kotlin Coroutines **/
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getNasaJsonResponse(@Query("earth_date") earthDate: String?,
                            @Query("sol") sol: Int?,
                            @Query("camera") camera: String?,
                            @Query("api_key") apiKey: String) : NetworkPhotoContainer

}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

object NasaApi {
    val RETROFIT_SERVICE: NasaWebAPI by lazy { retrofit.create(NasaWebAPI::class.java) }
}
