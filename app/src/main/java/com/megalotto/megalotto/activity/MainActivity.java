package com.megalotto.megalotto.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.fragment.HomeFragment;
import com.megalotto.megalotto.fragment.MyTicketFragment;
import com.megalotto.megalotto.fragment.ResultFragment;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Constants;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 218;
    private static final String TAG = "MainActivity";
    public static String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    public static String[] permissions_33 = {"android.permission.POST_NOTIFICATIONS", "android.permission.READ_MEDIA_IMAGES"};
    public MyApplication MyApp;
    long backPressed;
    public BottomNavigationView bottomNavigationView;
    private Context context;
    public DrawerLayout drawer;
    public TextView editTxt;
    public TextView emailTxt;
    public ActionBarDrawerToggle mDrawerToggle;
    public FirebaseAnalytics mFirebaseAnalytics;
    public TextView nameTxt;
    public NavigationView navigationView;
    private CircleImageView profileImg;
    public TextView profileTxt;
    public SwitchCompat switchNoti;
    public Toolbar toolbar;
    int startingPosition = 0;
    boolean isNotificationPermitted = false;
    boolean isStorageImagePermitted = false;
    private final ActivityResultLauncher<String> request_permission_launcher_notification = registerForActivityResult(new ActivityResultContracts.RequestPermission(), (ActivityResultCallback) obj -> MainActivity.this.m256lambda$new$5$comratechnoworldmegalottoactivityMainActivity((Boolean) obj));
    private final ActivityResultLauncher<String> request_permission_launcher_storage_image = registerForActivityResult(new ActivityResultContracts.RequestPermission(), (ActivityResultCallback) obj -> MainActivity.this.m257lambda$new$6$comratechnoworldmegalottoactivityMainActivity((Boolean) obj));


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        this.MyApp = MyApplication.getInstance();
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initSideNavigation();
        initBottomNavigation();
        initNotification();
        if (Build.VERSION.SDK_INT >= 33) {
            if (!this.isNotificationPermitted) {
                requestPermissionNotification33();
            } else if (!this.isStorageImagePermitted) {
                requestPermissionImage33();
            }
        } else if (ContextCompat.checkSelfPermission(this, permissions[0]) == 0 || ContextCompat.checkSelfPermission(this, permissions[1]) == 0 || ContextCompat.checkSelfPermission(this, permissions[2]) == 0) {
            Log.d(TAG, permissions[0] + " Granted");
            this.isStorageImagePermitted = true;
        } else {
            String[] strArr = permissions;
            ActivityCompat.requestPermissions(this, new String[]{strArr[0], strArr[1], strArr[2]}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void initSideNavigation() {
        this.toolbar = findViewById(R.id.toolbar);
        this.drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerClosed(View view) {
                    MainActivity.this.supportInvalidateOptionsMenu();
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    MainActivity.this.supportInvalidateOptionsMenu();
                }
            };
            this.mDrawerToggle = actionBarDrawerToggle;
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            this.drawer.setDrawerListener(this.mDrawerToggle);
            this.mDrawerToggle.syncState();
        }
        this.navigationView.setNavigationItemSelectedListener(this);
        View headerView = this.navigationView.getHeaderView(0);
        this.nameTxt = headerView.findViewById(R.id.nameTxt);
        this.nameTxt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME));
        this.emailTxt = headerView.findViewById(R.id.emailTxt);
        this.emailTxt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL));
        this.profileImg = headerView.findViewById(R.id.profileImg);
        this.profileTxt = headerView.findViewById(R.id.profileTxt);
        this.editTxt = headerView.findViewById(R.id.editTxt);
        this.editTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.m255x2084eb26(view);
            }
        });
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals("") || Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals(AppConstant.WEB_URL)) {
            this.profileTxt.setVisibility(View.VISIBLE);
            this.profileImg.setVisibility(View.GONE);
            try {
                this.profileTxt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME));
                return;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            }
        }
        this.profileImg.setVisibility(View.VISIBLE);
        this.profileTxt.setVisibility(View.GONE);
        try {
            Glide.with(getApplicationContext()).load(AppConstant.WEB_URL + Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE)).apply(new RequestOptions().override(60, 60)).apply(new RequestOptions().placeholder(R.drawable.app_icon).error(R.drawable.app_icon)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).into(this.profileImg);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }


    public void m255x2084eb26(View v) {
        Function.fireIntent(this, ProfileActivity.class);
        this.drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Function.fireIntent(this, NotificationActivity.class);
                return true;
            case R.id.action_wallet:
                Function.fireIntent(this, WalletActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                alertDialog().show();
                break;
            case R.id.menu_about:
                Function.fireIntent(this, AboutUsActivity.class);
                break;
            case R.id.menu_contact:
                Function.fireIntent(this, ContactUsActivity.class);
                break;
            case R.id.menu_policy:
                Function.fireIntent(this, PrivacyPolicyActivity.class);
                break;
            case R.id.menu_refer_earn:
                Function.fireIntent(this, ReferActivity.class);
                break;
            case R.id.menu_support:
                Function.fireIntent(this, SupportActivity.class);
                break;
            case R.id.menu_terms:
                Function.fireIntent(this, TermsConditionActivity.class);
                break;
        }
        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private AlertDialog alertDialog() {
        return new AlertDialog.Builder(this).setIcon(R.drawable.logout).setMessage("Are You Sure You Want to Logout?").setPositiveButton("Confirm", (dialogInterface, i) -> MainActivity.this.m252xca6897d6(dialogInterface, i)).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
    }


    public void m252xca6897d6(DialogInterface dialog, int which) {
        Preferences.getInstance(this).setlogout();
    }



    private void initBottomNavigation() {
        loadFragment(new HomeFragment(), 1);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        this.bottomNavigationView = bottomNavigationView;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                return MainActivity.this.m253x559b0efd(menuItem);
            }
        });
    }


    public boolean m253x559b0efd(MenuItem item) {
        Fragment fragment = null;
        int newPosition = 0;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                newPosition = 1;
                break;
            case R.id.nav_result:
                fragment = new ResultFragment();
                newPosition = 3;
                break;
            case R.id.nav_ticket:
                fragment = new MyTicketFragment();
                newPosition = 2;
                break;
        }
        return loadFragment(fragment, newPosition);
    }

    private boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if (this.startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.mainContainer, fragment);
                transaction.commit();
            }
            if (this.startingPosition < newPosition) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction2.replace(R.id.mainContainer, fragment);
                transaction2.commit();
            }
            this.startingPosition = newPosition;
            return true;
        }
        return false;
    }

    private void initNotification() {
        this.switchNoti = findViewById(R.id.switch_noti);
        this.switchNoti.setChecked(this.MyApp.getNotification());
        this.switchNoti.setOnCheckedChangeListener((compoundButton, z) -> MainActivity.this.m254x7bc51f8a(compoundButton, z));
    }


    public void m254x7bc51f8a(CompoundButton buttonView, boolean isChecked) {
        Log.e("aaa-noti", "" + isChecked);
        this.MyApp.saveIsNotification(isChecked);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
    }

    public void requestPermissionNotification33() {
        if (ContextCompat.checkSelfPermission(this, permissions_33[0]) == 0) {
            Log.d(TAG, permissions_33[0] + " Granted");
            this.isNotificationPermitted = true;
            return;
        }
        this.request_permission_launcher_notification.launch(permissions_33[0]);
    }


    public void m256lambda$new$5$comratechnoworldmegalottoactivityMainActivity(Boolean isGranted) {
        if (isGranted.booleanValue()) {
            Log.d(TAG, permissions_33[0] + " Granted");
            this.isNotificationPermitted = true;
            if (!this.isStorageImagePermitted) {
                requestPermissionImage33();
                return;
            }
            return;
        }
        Log.d(TAG, permissions_33[0] + " Not Granted");
        this.isNotificationPermitted = false;
        if (!this.isStorageImagePermitted) {
            requestPermissionImage33();
        }
    }

    public void requestPermissionImage33() {
        if (ContextCompat.checkSelfPermission(this, permissions_33[1]) == 0) {
            Log.d(TAG, permissions_33[1] + " Granted");
            this.isStorageImagePermitted = true;
            return;
        }
        this.request_permission_launcher_storage_image.launch(permissions_33[1]);
    }


    public void m257lambda$new$6$comratechnoworldmegalottoactivityMainActivity(Boolean isGranted) {
        if (isGranted) {
            Log.d(TAG, permissions_33[1] + " Granted");
            this.isStorageImagePermitted = true;
            return;
        }
        Log.d(TAG, permissions_33[1] + " Not Granted");
        this.isStorageImagePermitted = false;
    }

    @Override
    public void onBackPressed() {
        if (this.backPressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.setFlags(268435456);
            startActivity(intent);
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        this.backPressed = System.currentTimeMillis();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals("") || Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals(AppConstant.WEB_URL)) {
            this.profileTxt.setVisibility(View.VISIBLE);
            this.profileImg.setVisibility(View.GONE);
            try {
                Log.e(TAG, "onResume: "+Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME));
                String a = String.valueOf(Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME).charAt(0));
                this.profileTxt.setText(a);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.profileImg.setVisibility(View.VISIBLE);
        this.profileTxt.setVisibility(View.GONE);
        try {
            Glide.with(getApplicationContext()).load(AppConstant.WEB_URL + Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE)).apply(new RequestOptions().override(60, 60)).apply(new RequestOptions().placeholder(R.drawable.app_icon).error(R.drawable.app_icon)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).into(this.profileImg);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }
}
