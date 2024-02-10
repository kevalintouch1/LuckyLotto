package com.megalotto.megalotto.repository;

import androidx.lifecycle.MutableLiveData;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.model.Contest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BronzeRepository {
    private static BronzeRepository invoiceRepository;
    private final List<Contest> responseList = new ArrayList();
    private final ApiCalling apiCalling = MyApplication.getRetrofit().create(ApiCalling.class);

    public static BronzeRepository getInstance() {
        if (invoiceRepository == null) {
            invoiceRepository = new BronzeRepository();
        }
        return invoiceRepository;
    }

    private BronzeRepository() {
    }

    public MutableLiveData<List<Contest>> getNews(String contestId, String pkgId) {
        final MutableLiveData<List<Contest>> newsData = new MutableLiveData<>();
        Call<List<Contest>> call = this.apiCalling.getContest(AppConstant.PURCHASE_KEY, contestId, pkgId);
        call.enqueue(new Callback<List<Contest>>() {
            @Override 
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                BronzeRepository.this.responseList.clear();
                if (response.isSuccessful()) {
                    List<Contest> invoiceModels = response.body();
                    newsData.setValue(invoiceModels);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                BronzeRepository.this.responseList.clear();
                newsData.setValue(BronzeRepository.this.responseList);
            }
        });
        return newsData;
    }
}
