package com.megalotto.megalotto.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Constants;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.PicModeSelectDialogFragment;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.model.CustomerModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity implements PicModeSelectDialogFragment.IPicModeSelectListener {
    public static final String ERROR = "error";
    public static final String ERROR_MSG = "error_msg";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 218;
    public static final int REQUEST_CODE_PICK_GALLERY = 1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 2;
    private static final String TAG = "ProfileActivity";
    public static String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    public static String[] permissions_33 = {"android.permission.READ_MEDIA_IMAGES"};
    private ApiCalling api;
    public TextView changeTxt;
    private Context context;
    public EditText dateofbirthEdt;
    private int day;
    public EditText emailEt;
    public EditText genderEdt;
    public EditText mobileEdt;
    private int month;
    public EditText nameEdt;
    public EditText newPassEdt;
    private String newPassword;
    public EditText oldPassEdt;
    private String oldPassword;
    public CircleImageView profileImg;
    public TextView profileTxt;
    private ProgressBarHelper progressBarHelper;
    public EditText retypeNewPassEdt;
    private String retypeNewPassword;
    public ImageView updateImg;
    public TextView updateTxt;
    private String uriFile;
    private int year;
    public String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    boolean isStorageImagePermitted = false;

    private final ActivityResultLauncher<String> request_permission_launcher_storage_image = registerForActivityResult(new ActivityResultContracts.RequestPermission(), (ActivityResultCallback) obj -> ProfileActivity.this.m267xac9c67e9((Boolean) obj));


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initListener();
        this.mobileEdt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
        this.dateofbirthEdt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_DOB));
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME).equals("")) {
            this.nameEdt.setText("");
        } else {
            this.nameEdt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME));
        }
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL).equals("")) {
            this.emailEt.setText("");
        } else {
            this.emailEt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL));
        }
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_GENDER).equals("")) {
            this.genderEdt.setText("");
        } else {
            this.genderEdt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_GENDER));
        }
        if (Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals("") || Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE).equals(AppConstant.WEB_URL)) {
            this.profileTxt.setVisibility(View.VISIBLE);
            this.profileImg.setVisibility(View.GONE);
            try {
                this.profileTxt.setText(Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME));
                return;
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                return;
            }
        }
        this.profileTxt.setVisibility(View.GONE);
        this.profileImg.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(AppConstant.WEB_URL + Preferences.getInstance(this.context).getString(Preferences.KEY_RESORT_IMAGE)).apply(new RequestOptions().override(120, 120)).apply(new RequestOptions().placeholder(R.drawable.app_icon).error(R.drawable.app_icon)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).into(this.profileImg);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.nameEdt = findViewById(R.id.nameEdt);
        this.emailEt = findViewById(R.id.emailEt);
        this.mobileEdt = findViewById(R.id.mobileEdt);
        this.genderEdt = findViewById(R.id.genderEdt);
        this.dateofbirthEdt = findViewById(R.id.dateofbirthEdt);
        this.profileImg = findViewById(R.id.profileImg);
        this.profileTxt = findViewById(R.id.profileTxt);
        this.updateImg = findViewById(R.id.updateImg);
        this.updateTxt = findViewById(R.id.updateTxt);
        this.oldPassEdt = findViewById(R.id.oldPassEdt);
        this.newPassEdt = findViewById(R.id.newPassEdt);
        this.retypeNewPassEdt = findViewById(R.id.retypeNewPassEdt);
        this.changeTxt = findViewById(R.id.changeTxt);
    }

    private void initListener() {
        this.genderEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.m262x4f04ccb0(view);
            }
        });
        this.updateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.m263x69204b4f(view);
            }
        });
        this.updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.m264x833bc9ee(view);
            }
        });
        this.dateofbirthEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.m265x9d57488d(view);
            }
        });
        this.changeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.m266xb772c72c(view);
            }
        });
    }


    public void m262x4f04ccb0(View v) {
        final String[] fonts = {"Male", "Female"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        builder.setItems(fonts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProfileActivity.this.m261x34e94e11(fonts, dialogInterface, i);
            }
        });
        builder.show();
    }


    public void m261x34e94e11(String[] fonts, DialogInterface dialog, int which) {
        if ("Male".equals(fonts[which])) {
            this.genderEdt.setText("Male");
        } else if ("Female".equals(fonts[which])) {
            this.genderEdt.setText("Female");
        }
    }


    public void m263x69204b4f(View v) {
        if (Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME).equals("user")) {
            Toast.makeText(this, "For demo all insert/update/delete are disable!!!", Toast.LENGTH_SHORT).show();
        } else if (Build.VERSION.SDK_INT >= 33) {
            if (!this.isStorageImagePermitted) {
                requestPermissionImage33();
            } else {
                showAddProfilePicDialog();
            }
        } else if (ContextCompat.checkSelfPermission(this, permissions[0]) == 0 || ContextCompat.checkSelfPermission(this, permissions[1]) == 0 || ContextCompat.checkSelfPermission(this, permissions[2]) == 0) {
            showAddProfilePicDialog();
        } else {
            String[] strArr = permissions;
            ActivityCompat.requestPermissions(this, new String[]{strArr[0], strArr[1], strArr[2]}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    public void m264x833bc9ee(View v) {
        if (Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME).equals("user")) {
            Toast.makeText(this, "For demo all insert/update/delete are disable!!!", Toast.LENGTH_SHORT).show();
        } else {
            updateProfile();
        }
    }

    public void m265x9d57488d(View v) {
        setDateOfBirth();
    }


    public void m266xb772c72c(View v) {
        this.oldPassword = this.oldPassEdt.getText().toString();
        this.newPassword = this.newPassEdt.getText().toString();
        this.retypeNewPassword = this.retypeNewPassEdt.getText().toString();
        if (Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME).equals("user")) {
            Toast.makeText(this, "For demo all insert/update/delete are disable!!!", Toast.LENGTH_SHORT).show();
        } else {
            updatePassword();
        }
    }

    public void requestPermissionImage33() {
        if (ContextCompat.checkSelfPermission(this, permissions_33[0]) == 0) {
            Log.d(TAG, permissions_33[0] + " Granted");
            this.isStorageImagePermitted = true;
            showAddProfilePicDialog();
            return;
        }
        this.request_permission_launcher_storage_image.launch(permissions_33[0]);
    }


    public void m267xac9c67e9(Boolean isGranted) {
        if (isGranted.booleanValue()) {
            Log.d(TAG, permissions_33[0] + " Granted");
            this.isStorageImagePermitted = true;
            showAddProfilePicDialog();
            return;
        }
        Log.d(TAG, permissions_33[0] + " Not Granted");
        this.isStorageImagePermitted = false;
    }

    @Override
    public void onPicModeSelected(String mode) {
        String action = mode.equalsIgnoreCase(Constants.PicModes.CAMERA) ? Constants.IntentExtras.ACTION_CAMERA : Constants.IntentExtras.ACTION_GALLERY;
        if (action.equals(Constants.IntentExtras.ACTION_CAMERA)) {
            takePic();
        } else {
            pickImage();
        }
    }

    private void showAddProfilePicDialog() {
        PicModeSelectDialogFragment dialogFragment = new PicModeSelectDialogFragment();
        dialogFragment.setiPicModeSelectListener(this);
        dialogFragment.show(getFragmentManager(), "picModeSelector");
    }

    private void pickImage() {
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void takePic() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 2);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bmp != null) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, 0);
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == 2) {
            try {
                onCaptureImageResultInstrument(result);
            } catch (Exception e) {
                errorValidation();
            }
        } else if (requestCode == 1) {
            if (resultCode == 0) {
                userCancelled();
            } else if (resultCode == -1 && result != null && result.getData() != null) {
                try {
                    onGalleryImageResultInstrument(result);
                } catch (Exception e2) {
                    errorValidation();
                }
            } else {
                errorValidation();
            }
        }
    }

    public void userCancelled() {
    }

    public void errorValidation() {
        Intent intent = new Intent();
        intent.putExtra("error", true);
        intent.putExtra(ERROR_MSG, "Error while opening the image file. Please try again.");
        finish();
    }

    public void updateProfile() {
        String name;
        String emailID;
        String dob;
        String gender;
        if (this.emailEt.getText().toString().equals("") && this.dateofbirthEdt.getText().toString().equals("")) {
            Toast.makeText(this.context, "Enter valid input", Toast.LENGTH_SHORT).show();
        } else if (!this.emailEt.getText().toString().trim().matches(this.emailPattern)) {
            Toast.makeText(this.context, "Enter valid email id", Toast.LENGTH_SHORT).show();
        } else if (Function.checkNetworkConnection(this.context)) {
            if (this.nameEdt.getText().toString().equals("")) {
                name = Preferences.getInstance(this.context).getString(Preferences.KEY_FULL_NAME);
            } else {
                name = this.nameEdt.getText().toString();
            }
            if (this.emailEt.getText().toString().equals("")) {
                emailID = Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL);
            } else {
                emailID = this.emailEt.getText().toString();
            }
            if (this.dateofbirthEdt.getText().toString().equals("")) {
                dob = Preferences.getInstance(this.context).getString(Preferences.KEY_DOB);
            } else {
                dob = this.dateofbirthEdt.getText().toString();
            }
            if (this.genderEdt.getText().toString().equals("")) {
                gender = Preferences.getInstance(this.context).getString(Preferences.KEY_GENDER);
            } else {
                gender = this.genderEdt.getText().toString();
            }
            if (!Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL).equals(this.emailEt.getText().toString()) || !Preferences.getInstance(this.context).getString(Preferences.KEY_DOB).equals(this.dateofbirthEdt.getText().toString()) || !Preferences.getInstance(this.context).getString(Preferences.KEY_GENDER).equals(this.genderEdt.getText().toString())) {
                this.progressBarHelper.showProgressDialog();
                Call<CustomerModel> call = this.api.updateUserProfileDOB(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), name, emailID, gender, dob);
                call.enqueue(new Callback<CustomerModel>() {
                    @Override
                    public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                        CustomerModel legalData;
//                        ProfileActivity.this.progressBarHelper.hideProgressDialog();
//                        if (response.isSuccessful() && (legalData = response.body()) != null) {
//                            List<CustomerModel.Result> res = legalData.getResult();
//                            if (res.get(0).getSuccess() == 1) {
//                                Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_EMAIL, ProfileActivity.this.emailEt.getText().toString());
//                                Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_DOB, ProfileActivity.this.dateofbirthEdt.getText().toString());
//                                Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_FULL_NAME, ProfileActivity.this.nameEdt.getText().toString());
//                                Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_GENDER, ProfileActivity.this.genderEdt.getText().toString());
//                                Function.showToast(ProfileActivity.this.context, res.get(0).getMsg());
//                            }
//                        }
                    }

                    @Override 
                    public void onFailure(Call<CustomerModel> call2, Throwable t) {
                        ProfileActivity.this.progressBarHelper.hideProgressDialog();
                    }
                });
            }
        }
    }

    public boolean validatePassword() {
        if (this.oldPassword.isEmpty()) {
            this.oldPassEdt.setError("Enter current password");
            return false;
        } else if (this.newPassword.length() < 8 || this.newPassword.length() > 20) {
            this.newPassEdt.setError("Password must be 8 to 20 characters");
            return false;
        } else if (this.retypeNewPassword.isEmpty()) {
            this.retypeNewPassEdt.setError("Re-enter new password");
            return false;
        } else if (!this.oldPassword.equals(Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD))) {
            this.oldPassEdt.setError("Old password is incorrect.");
            return false;
        } else if (this.retypeNewPassword.equals(this.newPassword)) {
            return true;
        } else {
            this.retypeNewPassEdt.setError("New passwords don't match. Retry!");
            return false;
        }
    }

    public void updatePassword() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (validatePassword()) {
            this.progressBarHelper.showProgressDialog();
            Call<CustomerModel> call1 = this.api.updateUserProfilePassword(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), this.newPassword);
            call1.enqueue(new Callback<CustomerModel>() {
                @Override
                public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                    CustomerModel legalData;
                    ProfileActivity.this.progressBarHelper.hideProgressDialog();
                    if (response.isSuccessful() && (legalData = response.body()) != null) {
//                        List<CustomerModel.Result> res = legalData.getResult();
//                        if (res.get(0).getSuccess() == 1) {
//                            Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_PASSWORD, ProfileActivity.this.newPassEdt.getText().toString());
//                            Function.showToast(ProfileActivity.this.context, res.get(0).getMsg());
//                        }
                    }
                }

                @Override 
                public void onFailure(Call<CustomerModel> call, Throwable t) {
                    ProfileActivity.this.progressBarHelper.hideProgressDialog();
                }
            });
        }
    }

    public void updateUserPicture(String uriFile) {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> profileCall = this.api.updateUserPicture(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), uriFile);
        profileCall.enqueue(new Callback<List<Contest>>() { // from class: com.ratechnoworld.megalotto.activity.ProfileActivity.3
            @Override 
            public void onResponse(Call<List<Contest>> call, Response<List<Contest>> response) {
                List<Contest> legalData;
                ProfileActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    Preferences.getInstance(ProfileActivity.this.context).setString(Preferences.KEY_RESORT_IMAGE, "upload/avatar/" + Preferences.getInstance(ProfileActivity.this.context).getString(Preferences.KEY_USER_ID) + ".jpg");
                    Function.showToast(ProfileActivity.this.context, legalData.get(0).getMsg());
                    Function.fireIntent(ProfileActivity.this.context, MainActivity.class);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call, Throwable t) {
                ProfileActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    private void onCaptureImageResultInstrument(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        this.uriFile = getStringImage(bitmap);
        this.profileImg.setImageBitmap(bitmap);
        this.profileTxt.setVisibility(View.GONE);
        this.profileImg.setVisibility(View.VISIBLE);
        if (bitmap != null) {
            updateUserPicture(this.uriFile);
        }
    }

    private void onGalleryImageResultInstrument(Intent data) {
        Uri saveUri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), saveUri);
            this.uriFile = getStringImage(bitmap);
            this.profileImg.setImageBitmap(bitmap);
            this.profileTxt.setVisibility(View.GONE);
            this.profileImg.setVisibility(View.VISIBLE);
            if (saveUri != null) {
                updateUserPicture(this.uriFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDateOfBirth() {
        Calendar c = Calendar.getInstance();
        c.set(1, 1980);
        c.set(2, 0);
        c.set(5, 1);
        this.year = c.get(1);
        this.month = c.get(2);
        this.day = c.get(5);
        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() { // from class: com.ratechnoworld.megalotto.activity.ProfileActivity$$ExternalSyntheticLambda7
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                ProfileActivity.this.m268x8f47bb36(datePicker, i, i2, i3);
            }
        }, this.year, this.month, this.day);
        picker.show();
    }

    
    /* renamed from: lambda$setDateOfBirth$7$com-ratechnoworld-megalotto-activity-ProfileActivity  reason: not valid java name */
    public void m268x8f47bb36(DatePicker view, int year2, int monthOfYear, int dayOfMonth) {
        this.year = year2;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        this.dateofbirthEdt.setText(new StringBuilder().append(pad(this.month + 1)).append("/").append(pad(this.day)).append("/").append(this.year));
    }

    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        }
        return "0" + c;
    }
}
