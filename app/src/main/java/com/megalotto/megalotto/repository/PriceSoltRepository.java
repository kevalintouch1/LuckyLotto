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

public class PriceSoltRepository {
    private static PriceSoltRepository invoiceRepository;
    private final List<Contest> responseList = new ArrayList();
    private final ApiCalling apiCalling = (ApiCalling) MyApplication.getRetrofit().create(ApiCalling.class);

    public static PriceSoltRepository getInstance() {
        if (invoiceRepository == null) {
            invoiceRepository = new PriceSoltRepository();
        }
        return invoiceRepository;
    }

    private PriceSoltRepository() {
    }

    public MutableLiveData<List<Contest>> getNews(String ID) {
        final MutableLiveData<List<Contest>> newsData = new MutableLiveData<>();
        Call<List<Contest>> call = this.apiCalling.getPriceSlot(AppConstant.PURCHASE_KEY, ID);
        call.enqueue(new Callback<List<Contest>>() {
            @Override 
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                PriceSoltRepository.this.responseList.clear();
                if (response.isSuccessful()) {
                    List<Contest> invoiceModels = response.body();
                    newsData.setValue(invoiceModels);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                PriceSoltRepository.this.responseList.clear();
                newsData.setValue(PriceSoltRepository.this.responseList);
            }
        });
        return newsData;
    }
}
