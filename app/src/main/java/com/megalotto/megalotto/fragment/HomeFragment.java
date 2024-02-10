package com.megalotto.megalotto.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.MainActivity;
import com.megalotto.megalotto.adapter.ViewPagerAdapter;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.CustomerModel;
import com.megalotto.megalotto.model.Packages;
import com.megalotto.megalotto.ui.tickets.TicketsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Activity activity;
    private ViewPagerAdapter adapter;
    private ApiCalling api;
    public TextView contestTv;
    private CountDownTimer mCountDownTimer;
    public TimerListener mListener;
    public TextView noContentTv;
    public LinearLayout noDataLy;
    private ProgressBarHelper progressBarHelper;
    public TabLayout tabLayout;
    public TextView timerTv;
    public LinearLayout topPanel;
    View view;
    public ViewPager viewPager;
    private long mHours = 0;
    private long mMinutes = 0;
    private long mSeconds = 0;
    private long mMilliSeconds = 0;


    public interface TimerListener {
        void onFinish();

        void onTick(long j);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = context instanceof Activity ? (Activity) context : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.activity, false);
        initView();
        this.adapter = new ViewPagerAdapter(getChildFragmentManager(), this.activity, this.viewPager, this.tabLayout);
        this.viewPager.setOffscreenPageLimit(5);
        this.viewPager.setAdapter(this.adapter);
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                HomeFragment.this.viewPager.setCurrentItem(tab.getPosition());
                Log.d("Selected", "Selected " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("Reselected", "Reselected " + tab.getPosition());
            }
        });
        if (Function.checkNetworkConnection(this.activity)) {
            getLiveContest();
        } else {
            Toast.makeText(this.activity, "No internet Connection, please try again later.", Toast.LENGTH_SHORT).show();
        }
        return this.view;
    }

    private void initView() {
        this.tabLayout = this.view.findViewById(R.id.tabLayout);
        this.viewPager = this.view.findViewById(R.id.viewPager);
        this.contestTv = this.view.findViewById(R.id.contestTv);
        this.timerTv = this.view.findViewById(R.id.timerTv);
        this.topPanel = this.view.findViewById(R.id.topPanel);
        this.noDataLy = this.view.findViewById(R.id.noDataLy);
        this.noContentTv = this.view.findViewById(R.id.noContentTv);
    }

    public void addPage(String id, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("PKG_ID", id);
        bundle.putString("PKG_NAME", title);
        TicketsFragment dynamicFragment = new TicketsFragment();
        dynamicFragment.setArguments(bundle);
        Preferences.getInstance(this.activity).setString(Preferences.KEY_PKG_ID, id);
        this.adapter.addFrag(dynamicFragment, title);
        this.adapter.notifyDataSetChanged();
        if (this.adapter.getCount() > 0) {
            this.tabLayout.setupWithViewPager(this.viewPager);
        }
        this.viewPager.setCurrentItem(this.adapter.getCount() - 1);
    }

    private void getLiveContest() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.getLiveContest(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                CustomerModel legalData;
                HomeFragment.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() != 1) {
//                        HomeFragment.this.getUpcomingContest();
//                    } else if (res.get(0).getEnd_time() != null && res.get(0).getCurrent_time() != null) {
//                        Preferences.getInstance(HomeFragment.this.activity).setString(Preferences.KEY_CONTST_ID, res.get(0).getId());
//                        int time = Integer.parseInt(res.get(0).getEnd_time()) - Integer.parseInt(res.get(0).getCurrent_time());
//                        if (time <= 0) {
//                            HomeFragment.this.progressBarHelper.hideProgressDialog();
//                            HomeFragment.this.noContentTv.setText("Result will be\nannounce soon");
//                            HomeFragment.this.topPanel.setVisibility(View.GONE);
//                            HomeFragment.this.tabLayout.setVisibility(View.GONE);
//                            HomeFragment.this.viewPager.setVisibility(View.GONE);
//                            HomeFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                            return;
//                        }
//                        HomeFragment.this.contestTv.setText("Ends In - ");
//                        HomeFragment.this.topPanel.setVisibility(View.VISIBLE);
//                        HomeFragment.this.tabLayout.setVisibility(View.VISIBLE);
//                        HomeFragment.this.viewPager.setVisibility(View.VISIBLE);
//                        HomeFragment.this.noDataLy.setVisibility(View.GONE);
//                        if (Function.checkNetworkConnection(HomeFragment.this.activity)) {
//                            HomeFragment.this.setDynamicFragmentToTabLayout();
//                        } else {
//                            Toast.makeText(HomeFragment.this.activity, "No internet Connection, please try again later.", Toast.LENGTH_SHORT).show();
//                        }
//                        HomeFragment.this.setTime(time * 1000L);
//                        HomeFragment.this.startCountDown();
//                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                HomeFragment.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    public void setDynamicFragmentToTabLayout() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Packages>> call = this.api.getPackages(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<List<Packages>>() {
            @Override
            public void onResponse(Call<List<Packages>> call2, Response<List<Packages>> response) {
                List<Packages> legalData;
                HomeFragment.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null && legalData.size() > 0) {
                    for (int i = 0; i < legalData.size(); i++) {
                        String id = legalData.get(i).getId();
                        String title = legalData.get(i).getPkg_name();
                        HomeFragment.this.addPage(id, title);
                        HomeFragment.this.viewPager.setCurrentItem(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Packages>> call2, Throwable t) {
                HomeFragment.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    public void getUpcomingContest() {
        Call<CustomerModel> call = this.api.getUpcomingContest(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                if (response.isSuccessful()) {
                    CustomerModel legalData = response.body();
                    if (legalData == null) {
                        HomeFragment.this.noContentTv.setText("No Upcoming Contest");
                        HomeFragment.this.topPanel.setVisibility(View.GONE);
                        HomeFragment.this.tabLayout.setVisibility(View.GONE);
                        HomeFragment.this.viewPager.setVisibility(View.GONE);
                        HomeFragment.this.noDataLy.setVisibility(View.VISIBLE);
                        return;
                    }
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() != 1) {
//                        HomeFragment.this.noContentTv.setText("No Upcoming Contest");
//                        HomeFragment.this.topPanel.setVisibility(View.GONE);
//                        HomeFragment.this.tabLayout.setVisibility(View.GONE);
//                        HomeFragment.this.viewPager.setVisibility(View.GONE);
//                        HomeFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                    } else if (res.get(0).getStart_time() != null && res.get(0).getCurrent_time() != null) {
//                        int time = Integer.parseInt(res.get(0).getStart_time()) - Integer.parseInt(res.get(0).getCurrent_time());
//                        if (time <= 0) {
//                            HomeFragment.this.progressBarHelper.hideProgressDialog();
//                            HomeFragment.this.noContentTv.setText("Contest will start soon");
//                            HomeFragment.this.topPanel.setVisibility(View.GONE);
//                            HomeFragment.this.tabLayout.setVisibility(View.GONE);
//                            HomeFragment.this.viewPager.setVisibility(View.GONE);
//                            HomeFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                            return;
//                        }
//                        HomeFragment.this.contestTv.setText("Next contest starts in: ");
//                        HomeFragment.this.topPanel.setVisibility(View.VISIBLE);
//                        HomeFragment.this.tabLayout.setVisibility(View.GONE);
//                        HomeFragment.this.viewPager.setVisibility(View.GONE);
//                        HomeFragment.this.noContentTv.setText("Upcoming contest will\nstart soon");
//                        HomeFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                        HomeFragment.this.setTime(time * 1000L);
//                        HomeFragment.this.startCountDown();
//                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
            }
        });
    }

    private void initCounter() {
        this.mCountDownTimer = new CountDownTimer(this.mMilliSeconds, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                HomeFragment homeFragment = HomeFragment.this;
                homeFragment.calculateTime(millisUntilFinished, homeFragment.timerTv);
                if (HomeFragment.this.mListener != null) {
                    HomeFragment.this.mListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                HomeFragment homeFragment = HomeFragment.this;
                homeFragment.calculateTime(0L, homeFragment.timerTv);
                if (HomeFragment.this.mListener != null) {
                    HomeFragment.this.mListener.onFinish();
                }
                Function.fireIntent(HomeFragment.this.activity, MainActivity.class);
            }
        };
    }

    public void startCountDown() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    public void setTime(long milliSeconds) {
        this.mMilliSeconds = milliSeconds;
        initCounter();
        calculateTime(milliSeconds, this.timerTv);
    }

    public void calculateTime(long milliSeconds, TextView timeText) {
        this.mSeconds = (milliSeconds / 1000) % 60;
        this.mMinutes = (milliSeconds / 60000) % 60;
        this.mHours = milliSeconds / 3600000;
        displayText(timeText);
    }

    private void displayText(TextView timeText) {
        try {
            String stringBuilder = getTwoDigitNumber(this.mHours) + ":" + getTwoDigitNumber(this.mMinutes) + ":" + getTwoDigitNumber(this.mSeconds) + "";
            timeText.setText(stringBuilder);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String getTwoDigitNumber(long number) {
        if (number >= 0 && number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
