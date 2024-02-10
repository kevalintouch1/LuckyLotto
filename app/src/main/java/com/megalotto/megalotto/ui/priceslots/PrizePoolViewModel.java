package com.megalotto.megalotto.ui.priceslots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.repository.PriceSoltRepository;

import java.util.List;


public class PrizePoolViewModel extends ViewModel {
    private MutableLiveData<List<Contest>> arrayInvoiceMutableLiveData;

    
    public void init(String ID) {
        if (this.arrayInvoiceMutableLiveData != null) {
            return;
        }
        PriceSoltRepository newsRepository = PriceSoltRepository.getInstance();
        this.arrayInvoiceMutableLiveData = newsRepository.getNews(ID);
    }

    
    public LiveData<List<Contest>> getDomesticList() {
        return this.arrayInvoiceMutableLiveData;
    }
}
