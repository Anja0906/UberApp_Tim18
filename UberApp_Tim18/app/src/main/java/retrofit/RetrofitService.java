package retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30, TimeUnit.MINUTES);
        httpClient.writeTimeout(30, TimeUnit.MINUTES);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.177.204:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void onSavedUser(String token){
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.177.204:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
