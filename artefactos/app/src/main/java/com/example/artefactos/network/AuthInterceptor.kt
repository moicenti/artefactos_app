import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        if (!token.isNullOrEmpty()) {
            builder.header("Authorization", "Bearer $token")
        }

        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}
