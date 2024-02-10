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

public class TicketSoldRepository {
    private static TicketSoldRepository invoiceRepository;
    private final List<Contest> responseList = new ArrayList();
    private final ApiCalling apiCalling = MyApplication.getRetrofit().create(ApiCalling.class);

    public static TicketSoldRepository getInstance() {
        if (invoiceRepository == null) {
            invoiceRepository = new TicketSoldRepository();
        }
        return invoiceRepository;
    }

    private TicketSoldRepository() {
    }

    public MutableLiveData<List<Contest>> getNews(String contestId, String FeesId) {
        final MutableLiveData<List<Contest>> newsData = new MutableLiveData<>();
        Call<List<Contest>> call = this.apiCalling.getTicketSold(AppConstant.PURCHASE_KEY, contestId, FeesId);
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                TicketSoldRepository.this.responseList.clear();
                if (response.isSuccessful()) {
                    List<Contest> invoiceModels = response.body();
                    newsData.setValue(invoiceModels);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                TicketSoldRepository.this.responseList.clear();
                newsData.setValue(TicketSoldRepository.this.responseList);
            }
        });
        return newsData;
    }
}
