package com.megalotto.megalotto;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megalotto.megalotto.helper.AppConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes8.dex */
public class MyApplication extends MultiDexApplication {
    public static Gson gson;
    public static MyApplication mInstance;
    private static Retrofit retrofit;
    public String prefName = "DreamJob";
    public SharedPreferences preferences;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initRetrofit();
        initGson();
    }

    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        MyApplication myApplication;
        synchronized (MyApplication.class) {
            myApplication = mInstance;
        }
        return myApplication;
    }

    public void saveIsNotification(boolean flag) {
        SharedPreferences sharedPreferences = getSharedPreferences(this.prefName, 0);
        this.preferences = sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IsNotification", flag);
        editor.apply();
    }

    public boolean getNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences(this.prefName, 0);
        this.preferences = sharedPreferences;
        return sharedPreferences.getBoolean("IsNotification", true);
    }

    private void initRetrofit() {
        Interceptor interceptor = new Interceptor() { // from class: com.ratechnoworld.megalotto.MyApplication$$ExternalSyntheticLambda0
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                try {
                    return MyApplication.lambda$initRetrofit$0(chain);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(60L, TimeUnit.SECONDS).connectTimeout(60L, TimeUnit.SECONDS).addInterceptor(interceptor).cache(null).build();
        retrofit = new Retrofit
                .Builder()
                .baseUrl(AppConstant.API_PATH)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    
    public static Response lambda$initRetrofit$0(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder().addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        Request request2 = builder.build();
        return chain.proceed(request2);
    }

    private void initGson() {
        gson = new GsonBuilder().setLenient().create();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
