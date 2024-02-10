package com.megalotto.megalotto.ui.ticketsold;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.repository.TicketSoldRepository;

import java.util.List;


public class TicketsSoldViewModel extends ViewModel {
    private MutableLiveData<List<Contest>> arrayInvoiceMutableLiveData;

    public void init(String contestId, String FeesId) {
        if (this.arrayInvoiceMutableLiveData != null) {
            return;
        }
        TicketSoldRepository newsRepository = TicketSoldRepository.getInstance();
        this.arrayInvoiceMutableLiveData = newsRepository.getNews(contestId, FeesId);
    }

    
    public LiveData<List<Contest>> getDomesticList() {
        return this.arrayInvoiceMutableLiveData;
    }
}
