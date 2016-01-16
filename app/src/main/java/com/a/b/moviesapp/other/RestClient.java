package com.a.b.moviesapp.other;

import android.util.Log;

import com.google.gson.Gson;
import retrofit.Call;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Andrew on 1/14/2016.
 */
public class RestClient {

    private static ApiInterface mApiInterface ;
//    private static String baseUrl = "https://api.github.com" ;

    public static ApiInterface getClient() {
        String TAG="RestClient";

        HttpLoggingInterceptor logging= new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient=new OkHttpClient();
        httpClient.interceptors().add(logging);

        if (mApiInterface == null) {

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return null;
                }
            });
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            mHttpClient.interceptors().add(interceptor);


//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                    .create();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Constants.TRAILER_BASE_URL)
                    .addConverter(String.class, new ToStringConverter())
                    .client(httpClient)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiInterface = client.create(ApiInterface.class);
            Log.e(TAG,"RestClient testing log statement: "+client.baseUrl()+", "+httpClient.getDns());
        }

        return mApiInterface ;
    }
}
