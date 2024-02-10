package com.megalotto.megalotto.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.repository.BronzeRepository;

import java.util.List;


public class TicketsViewModel extends ViewModel {
    private MutableLiveData<List<Contest>> arrayInvoiceMutableLiveData;

    
    public void init(String contestId, String pkgId) {
        if (this.arrayInvoiceMutableLiveData != null) {
            return;
        }
        BronzeRepository newsRepository = BronzeRepository.getInstance();
        this.arrayInvoiceMutableLiveData = newsRepository.getNews(contestId, pkgId);
    }

    
    public LiveData<List<Contest>> getDomesticList() {
        return this.arrayInvoiceMutableLiveData;
    }
}
