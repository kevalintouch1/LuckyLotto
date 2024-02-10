package com.megalotto.megalotto.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.paytm.pgsdk.PaytmConstants;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.ui.priceslots.PrizePoolFragment;
import com.megalotto.megalotto.ui.ticketsold.TicketsSoldFragment;
import com.megalotto.megalotto.utils.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TicketDetailActivity extends AppCompatActivity {
    public ApiCalling api;
    public Bundle bundle;
    TextView buyTxt;
    private Context context;
    public String entreeFees;
    TextView feeTxt;
    public String feedId;
    public String firstPrize;
    TextView firstPrizeTxt;
    TextView leaderboardTxt;
    TextView prizeTxt;
    ProgressBar progressBar;
    public ProgressBarHelper progressBarHelper;
    TextView remainTxt;
    public String status;
    TextView totalBoughTxt;
    public String totalNoBought;
    public String totalNoSold;
    public String totalNoTickets;
    public String totalNoWinner;
    public String totalPrize;
    TextView totalTxt;
    TextView totalWinnerTxt;
    private NonSwipeableViewPager viewPager;
    TextView winningTxt;

    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        this.bundle = new Bundle();
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initPreference();
        initListener();
        PrizePoolFragment priceSlotsFragment = new PrizePoolFragment();
        this.bundle.putString("FEES_ID", this.feedId);
        priceSlotsFragment.setArguments(this.bundle);
        TicketsSoldFragment ticketsSoldFragment = new TicketsSoldFragment();
        this.bundle.putString("FEES_ID", this.feedId);
        ticketsSoldFragment.setArguments(this.bundle);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(priceSlotsFragment, "Winning Breakup");
        pagerAdapter.addFragment(ticketsSoldFragment, "Leaderboard");
        this.viewPager.setOffscreenPageLimit(2);
        this.viewPager.setAdapter(pagerAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Details");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.viewPager = findViewById(R.id.viewPager);
        this.prizeTxt = findViewById(R.id.prizeTxt);
        this.feeTxt = findViewById(R.id.feeTxt);
        this.remainTxt = findViewById(R.id.remainTxt);
        this.totalTxt = findViewById(R.id.totalTxt);
        this.progressBar = findViewById(R.id.progressBar);
        this.firstPrizeTxt = findViewById(R.id.firstPrizeTxt);
        this.totalWinnerTxt = findViewById(R.id.totalWinnerTxt);
        this.totalBoughTxt = findViewById(R.id.totalBoughTxt);
        this.buyTxt = findViewById(R.id.buyTxt);
        this.winningTxt = findViewById(R.id.winningTxt);
        this.leaderboardTxt = findViewById(R.id.leaderboardTxt);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("TAG").equals("0")) {
                this.feedId = extras.getString("FEES_ID");
                this.entreeFees = extras.getString("ENTREE_FEES");
                this.totalPrize = extras.getString("TOTAL_PRIZE");
                this.totalNoTickets = extras.getString("TOTAL_TICKET");
                this.totalNoWinner = extras.getString("TOTAL_WINNERS");
                this.totalNoSold = extras.getString("TOTAL_SOLD");
                this.totalNoBought = extras.getString("TOTAL_BOUGHT");
                this.status = extras.getString(PaytmConstants.STATUS);
                Preferences.getInstance(this.context).setString(Preferences.KEY_PRICE, this.entreeFees);
                Preferences.getInstance(this.context).setString(Preferences.KEY_WINNER, this.totalNoWinner);
                Preferences.getInstance(this.context).setString(Preferences.KEY_SOLD, this.totalNoSold);
                this.prizeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.totalPrize);
                this.feeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.entreeFees);
                this.remainTxt.setText((Integer.parseInt(this.totalNoTickets) - Integer.parseInt(this.totalNoSold)) + " tickets left");
                this.totalTxt.setText(this.totalNoTickets + "  tickets");
                this.totalBoughTxt.setText(this.totalNoBought + " Bought");
                this.totalWinnerTxt.setText(this.totalNoWinner + " Winners");
                this.progressBar.setMax(Integer.parseInt(this.totalNoTickets));
                this.progressBar.setProgress(Integer.parseInt(this.totalNoSold));
                this.firstPrizeTxt.setVisibility(View.GONE);
                if (Integer.parseInt(this.totalNoSold) >= Integer.parseInt(this.totalNoTickets)) {
                    this.buyTxt.setText("Sold Out");
                    this.buyTxt.setEnabled(false);
                    return;
                } else if (this.status.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    this.buyTxt.setText("Buy Ticket");
                    this.buyTxt.setEnabled(true);
                    return;
                } else {
                    this.buyTxt.setText("Closed");
                    this.buyTxt.setEnabled(false);
                    return;
                }
            }
            this.feedId = extras.getString("FEES_ID");
            this.entreeFees = extras.getString("ENTREE_FEES");
            this.totalPrize = extras.getString("TOTAL_PRIZE");
            this.firstPrize = extras.getString("FIRST_PRIZE");
            this.totalNoTickets = extras.getString("TOTAL_TICKET");
            this.totalNoWinner = extras.getString("TOTAL_WINNERS");
            this.totalNoSold = extras.getString("TOTAL_SOLD");
            Preferences.getInstance(this.context).setString(Preferences.KEY_PRICE, this.entreeFees);
            Preferences.getInstance(this.context).setString(Preferences.KEY_WINNER, this.totalNoWinner);
            Preferences.getInstance(this.context).setString(Preferences.KEY_SOLD, this.totalNoSold);
            this.prizeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.totalPrize);
            this.feeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.entreeFees);
            this.remainTxt.setText((Integer.parseInt(this.totalNoTickets) - Integer.parseInt(this.totalNoSold)) + " tickets left");
            this.totalTxt.setText(this.totalNoTickets + "  tickets");
            this.firstPrizeTxt.setText("1st Prize: " + AppConstant.CURRENCY_SIGN + "" + this.firstPrize);
            this.totalWinnerTxt.setText(this.totalNoWinner + " Winners");
            this.progressBar.setMax(Integer.parseInt(this.totalNoTickets));
            this.progressBar.setProgress(Integer.parseInt(this.totalNoSold));
            this.totalBoughTxt.setVisibility(View.GONE);
            if (Integer.parseInt(this.totalNoSold) < Integer.parseInt(this.totalNoTickets)) {
                this.buyTxt.setText("Buy Ticket");
                this.buyTxt.setEnabled(true);
                return;
            }
            this.buyTxt.setText("Sold Out");
            this.buyTxt.setEnabled(false);
        }
    }

    private void initListener() {
        this.buyTxt.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                TicketDetailActivity.this.m285xb4c5613(view);
            }
        });
        this.winningTxt.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                TicketDetailActivity.this.m286x1c0222d4(view);
            }
        });
        this.leaderboardTxt.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                TicketDetailActivity.this.m287x2cb7ef95(view);
            }
        });
    }

    
    public void m285xb4c5613(View v) {
        buy();
    }


    public void m286x1c0222d4(View v) {
        this.winningTxt.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_button));
        this.leaderboardTxt.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_tab_unselected));
        this.winningTxt.setTextColor(ContextCompat.getColor(this.context, R.color.white));
        this.leaderboardTxt.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextGaryDark));
        this.viewPager.setCurrentItem(0);
    }

    public void m287x2cb7ef95(View v) {
        this.winningTxt.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_tab_unselected));
        this.leaderboardTxt.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_button));
        this.winningTxt.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextGaryDark));
        this.leaderboardTxt.setTextColor(ContextCompat.getColor(this.context, R.color.white));
        this.viewPager.setCurrentItem(1);
    }

    public void buy() {
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        intent.putExtra("FEES_ID", this.feedId);
        intent.putExtra("ENTREE_FEES", this.entreeFees);
        Preferences.getInstance(this.context).setString(Preferences.KEY_FEE_ID, this.feedId);
        Function.fireIntentWithData(this.context, intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    
    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.mFragmentList = new ArrayList();
            this.mFragmentTitleList = new ArrayList();
        }

        @Override
        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }
}
