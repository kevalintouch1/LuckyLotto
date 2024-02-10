package com.megalotto.megalotto.api;

import com.megalotto.megalotto.model.AppModel;
import com.megalotto.megalotto.model.ConfigurationModel;
import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.model.CustomerModel;
import com.megalotto.megalotto.model.CustomerResponseModel;
import com.megalotto.megalotto.model.NotificationModel;
import com.megalotto.megalotto.model.Packages;
import com.megalotto.megalotto.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiCalling {
    @FormUrlEncoded
    @POST(ApiConstant.ADD_TRANSACTION)
    Call<CustomerModel> addDepositTransaction(@Field("access_key") String str, @Field("id") String str2, @Field("payment_id") String str3, @Field("req_amount") String str4, @Field("getway_name") String str5);

    @FormUrlEncoded
    @POST(ApiConstant.ADD_TICKET)
    Call<CustomerModel> addTicket(@Field("access_key") String str, @Field("id") String str2, @Field("username") String str3, @Field("contest_id") String str4, @Field("fees_id") String str5, @Field("entry_fee") String str6, @Field("ticket_no") String str7);

    @FormUrlEncoded
    @POST(ApiConstant.ADD_TRANSACTION)
    Call<CustomerModel> addWithdrawTransaction(@Field("access_key") String str, @Field("id") String str2, @Field("request_name") String str3, @Field("req_from") String str4, @Field("req_amount") String str5, @Field("getway_name") String str6);

    @FormUrlEncoded
    @POST(ApiConstant.REGISTRATION_USER)
    Call<CustomerModel> customerRegistrationWithRefer(@Field("access_key") String str, @Field("name") String str2, @Field("email") String str3,
                                                      @Field("device_id") String str4, @Field("username") String str5, @Field("password") String str6,
                                                      @Field("country_code") String str7, @Field("mobile") String str8, @Field("referer") String str9,
                                                      @Field("fcm_token") String str10);

    //
    @FormUrlEncoded
    @POST(ApiConstant.REGISTRATION_USER)
    Call<CustomerModel> customerRegistrationWithoutRefer(@Field("mobile") String mobile,
                                                         @Field("email") String email,
                                                         @Field("password") String password,
                                                         @Field("referral") String referral,
                                                         @Field("fcmToken") String fcmToken);

    @FormUrlEncoded
    @POST(ApiConstant.REGISTRATION_USER)
    Call<CustomerResponseModel> customerErrorRegistration(@Field("mobile") String mobile,
                                                          @Field("email") String email,
                                                          @Field("password") String password,
                                                          @Field("referral") String referral,
                                                          @Field("fcmToken") String fcmToken);

    //
    @FormUrlEncoded
    @POST("paytmallinone/init_Transaction.php")
    Call<Token> generateTokenCall(@Field("code") String str, @Field("MID") String str2, @Field("ORDER_ID") String str3, @Field("AMOUNT") String str4);

    @GET(ApiConstant.GET_ABOUT_US)
    Call<ConfigurationModel> getAboutUs(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_APP_DETAILS)
    Call<AppModel> getAppDetails(@Query("access_key") String str);

    @GET(ApiConstant.GET_CONTACT_US)
    Call<ConfigurationModel> getContactUs(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_CONTEST)
    Call<List<Contest>> getContest(@Query("access_key") String str, @Query("contest_id") String str2, @Query("pkg_id") String str3);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_CONTEST_STATUS)
    Call<CustomerModel> getContestStatus(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_HISTORY)
    Call<List<Contest>> getHistory(@Query("access_key") String str, @Query("user_id") String str2);

    @GET(ApiConstant.GET_LIVE_CONTEST)
    Call<CustomerModel> getLiveContest(@Header("Authorization") String token);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_MY_RESULTS)
    Call<List<Contest>> getMyResults(@Query("access_key") String str, @Query("user_id") String str2, @Query("contest_id") String str3);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_MY_TICKET)
    Call<List<Contest>> getMyTicket(@Query("access_key") String str, @Query("user_id") String str2, @Query("contest_id") String str3);

    @GET(ApiConstant.GET_NOTIFICATION)
    Call<List<NotificationModel>> getNotification(@Header("Authorization") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_PACKAGES)
    Call<List<Packages>> getPackages(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_PRICE_SLOT)
    Call<List<Contest>> getPriceSlot(@Query("access_key") String str, @Query("fees_id") String str2);

    @GET(ApiConstant.GET_PRIVACY_POLICY)
    Call<ConfigurationModel> getPrivacyPolicy(@Query("access_key") String str);

    @GET(ApiConstant.GET_TERMS_CONDITION)
    Call<ConfigurationModel> getTermsCondition(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_TICKET_SOLD)
    Call<List<Contest>> getTicketSold(@Query("access_key") String str, @Query("contest_id") String str2, @Query("fees_id") String str3);

    @GET(ApiConstant.GET_TOP_WINNERS)
    Call<List<Contest>> getTopWinners(@Query("access_key") String str, @Query("contest_id") String str2, @Query("fees_id") String str3);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_TRANSACTION)
    Call<List<Contest>> getTransactions(@Query("access_key") String str, @Query("user_id") String str2);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.GET_UPCOMING_CONTEST)
    Call<CustomerModel> getUpcomingContest(@Query("access_key") String str);

    @Headers({"Cache-Control: no-cache"})
    @GET(ApiConstant.USER_WALLET)
    Call<CustomerModel> getUserWallet(@Query("access_key") String str, @Query("id") String str2);

    @GET(ApiConstant.LOGIN_USER)
    Call<CustomerModel> loginUser(@Query("access_key") String str, @Query("mobile") String str2, @Query("password") String str3);

    //
    @FormUrlEncoded
    @POST(ApiConstant.UPDATE_USER_PHOTO)
    Call<List<Contest>> updateUserPicture(@Field("access_key") String str, @Field("id") String str2, @Field("user_profile") String str3);

    //
    @FormUrlEncoded
    @POST(ApiConstant.UPDATE_USER_PROFILE)
    Call<CustomerModel> updateUserProfileDOB(@Field("access_key") String str, @Field("id") String str2, @Field("name") String str3,
                                             @Field("email") String str4, @Field("gender") String str5, @Field("dob") String str6);

    //
    @FormUrlEncoded
    @POST(ApiConstant.UPDATE_USER_PROFILE)
    Call<CustomerModel> updateUserProfileFCM(@Field("access_key") String str, @Field("id") String str2, @Field("fcm_token") String str3);

    //
    @FormUrlEncoded
    @POST(ApiConstant.UPDATE_USER_PROFILE)
    Call<CustomerModel> updateUserProfilePassword(@Field("access_key") String str, @Field("id") String str2, @Field("password") String str3);

    //
    @FormUrlEncoded
    @POST(ApiConstant.FORGOT_PASSWORD)
    Call<CustomerModel> userForgotPassword(@Field("access_key") String str, @Field("mobile") String str2, @Field("password") String str3);

    //
    @PUT(ApiConstant.VERIFY_MOBILE)
    Call<CustomerModel> verifyUserMobile(@Header("Authorization") String token,
                                         @Query("mobile") String mobile,
                                         @Query("password") String password);

    @PUT(ApiConstant.FORGOT_PWD)
    Call<CustomerModel> changeForgotPWD(@Header("Authorization") String token,
                                         @Query("email") String email,
                                         @Query("oldPassword") String oldPassword,
                                         @Query("newPassword") String newPassword,
                                         @Query("confirmPassword") String confirmPassword);
    //
}
