package com.webmyne.kidscrown.helper;

/**
 * @author jatin
 */
public class Constants {

    // utils
    public static final boolean LOGGING_ENABLED = true;
    public static final String TAG = "KidsCrown";

//    public static final String BASE_URL = "http://ws-srv-net.in.webmyne.com/Applications/KidsCrown/KidsCrownWS_V01/Services";
   public static final String BASE_URL = "http://ws.kidscrown.in/Services";

    public static final String REGISTRATION_URL = BASE_URL + "/User.svc/json/UserRegistration";
    public static final String LOGIN_URL = BASE_URL + "/User.svc/json/UserLogin/";
    public static final String SOCIAL_MEDIA_LOGIN_URL = BASE_URL + "/User.svc/json/LoginWithSocialMedia/";
    public static final String SOCIAL_MEDIA_SIGN_UP = BASE_URL + "/User.svc/json/SignUpWithSocialMedia/";

    public static final String SALUTATION_URL = BASE_URL + "/User.svc/json/FetchAllCodeCommonList/Salutation";
    public static final String QUALIFICATION_URL = BASE_URL + "/User.svc/json/FetchAllCodeCommonList/Qualification";
    public static final String FETCH_PRODUCTS = BASE_URL + "/Master.svc/json/FetchCurrentPricingForMobile";
    public static final String UPDATE_URL = BASE_URL + "/User.svc/json/UpdateUserProfile";
    public static final String GET_EXISTING_ADDRESS = BASE_URL + "/Master.svc/json/FetchShippingAddresses";

    public static final String SAVE_ADDRESS_URL = BASE_URL + "/Master.svc/json/AddNewShippingAddress";

    public static final String PLACE_ORDER = BASE_URL + "/Order.svc/json/PlaceOrder";

    public static final String FORGOT_PASSWORD_URL = BASE_URL + "/User.svc/json/ForgotPassword/";

    public static final String CROWN_PRODUCT_NAME = "Refill";

    public static final String FETCH_ORDER = BASE_URL + "/Order.svc/json/FetchOrdersApp?UserId=";

    public static String regNoPattern = "^[A-Z][0-9]{6}$";


}