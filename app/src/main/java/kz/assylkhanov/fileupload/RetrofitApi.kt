package kz.assylkhanov.fileupload

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @author a.asylkhanov
 */
interface RetrofitApi {

    @Multipart
    @POST("api/v1/files/upload")
    suspend fun uploadFile(
        @Part filePart: MultipartBody.Part
    )

    companion object {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co")
            .client(okHttpClient)
            .build()
        val service = retrofit.create(RetrofitApi::class.java)
    }
}
