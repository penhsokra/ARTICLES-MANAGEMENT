package camdev.sokra.topnews.auth;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "http://www.api-ams.me";
    private static final String API_KEY="XStdN15eKUmshN2TS4YTWYysgPXAp1Sbbfpjsnc131vrq8ii5T";
    //private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
    /* Rx java */
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass){
        okHttp.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder()
                        .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", API_KEY)
                        .header("Accept", "application/json")
                        .method(request.method(),request.body());
                return chain.proceed(requestBuilder.build());
            }
        });
        //return builder.client(okHttp.build()).build().create(serviceClass);
        return builder.build().create(serviceClass);
    }
}

