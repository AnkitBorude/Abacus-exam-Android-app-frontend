package com.example.abacusapplication.data;

import static androidx.core.content.ContentProviderCompat.requireContext;


import android.util.Log;
import android.widget.Toast;

import com.example.abacusapplication.MainActivity;
import com.example.abacusapplication.models.ApiError;

import java.lang.annotation.Annotation;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // 1. Base URL for all API calls
    private String baseurl;

    // 2. Singleton instance
    private static RetrofitClient instance;

    // 3. Retrofit object
    private Retrofit retrofit;

    // 4. Private constructor for Singleton pattern
    private RetrofitClient(String baseurl) {
        this.baseurl=" ";
        this.baseurl="http://"+baseurl+"/api/v1/";
        // 5. Logging interceptor setup
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

       Log.d("Baseurl",this.baseurl);
        // 6. OkHttpClient setup with interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                // Additional options:
                // .connectTimeout(30, TimeUnit.SECONDS)
                // .readTimeout(30, TimeUnit.SECONDS)
                // .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // 7. Retrofit builder
        retrofit = new Retrofit.Builder()
                .baseUrl(this.baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    // 8. Singleton getInstance method
    public static synchronized  RetrofitClient updateInstance(String baseurl)
    {
        instance=null;
        instance=new RetrofitClient(baseurl);
        return instance;
    }
    public static synchronized RetrofitClient getInstance(){
        if (instance == null) {
       return null;
        }
        return instance;
    }
    public ApiError convertError(okhttp3.ResponseBody body)
    {
        try
        {
            Converter<ResponseBody, ApiError> converter = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
            return converter.convert(body);
        }
        catch(Exception e)
        {
           e.printStackTrace();
            return null;
        }
    }
    public ApiService getApi() {
        return retrofit.create(ApiService.class);
    }

}
