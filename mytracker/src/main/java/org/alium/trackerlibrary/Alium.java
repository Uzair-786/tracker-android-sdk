package org.alium.trackerlibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.androi

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author UzairWani
 *
 * This class gets all the data from the application for tracking the user activities
 * on an integerated application
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Alium {

    private Context context;
    private Activity activity ;// = (Activity)context;

    public Alium (Activity context){
        this.activity = context;
    }


    private final static String ID_TAG = "ID";

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    private Float[] geolocation;

    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationManager locationManager;
//    private Location location;
//    private LocationListener locationListenerGPS;
//    private RequestQueue requestQueue;
      private LocationRequest locationRequest;

    private final OkHttpClient httpClient = new OkHttpClient();

    public Context setContextApp(Context context) {
       return this.context = context;
    }

    public Context getContextApp() {
        return context;
    }


    private String[] dim = new String [] {"", "button", "FCM Token", "×"}; //null;               //Element on which action is done.
    private String did = "";                 //device_id || android_id
    private String bvrs = "";                //build_version
    private String pth = "com.example.loginapp.MainActivity";//null;                 //screen/path/route
    private String scrnsz = "";              //screen_size
    private String orgs = "";                //operating_system
    private Float[] gloc = new Float[]{77.22222222222f,77.62626262626f};             //geo_location       --------- Based on App Permissions
    private String st = "";                  //state              --------- Based on App Permissions
    private String ct = "";                  //city               --------- Based on App Permissions
    private String ctry = "";                //country            --------- Based on App Permissions
    private String rgn = "";                 //region             --------- Based on App Permissions
    private String ntwp = "";                //network provider   --------- Based on App Permissions
    private String ssn ="sd4xg5s-44f5-54edf-65d65" ;//null;                 //session
    private String tsls = "01:50 pm";//null;    //time since last login/session
    private String aId = "";                 //app_id
    private String aitd = "";                 //app install date
    private String hnm = "My Local Host"; //  null;                 //current_hostname
    private String uia = "";                 //user ip_address
    private static String vstid ;              //visitor id
    private String ua = "";                  //user_agent
    private String cmp = "";                 //company_name
    private Integer tz = 0;                  //timezone
    private String evnt = "click";//null;      //event_name
    private String fcm = "";                   //FCM token




    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
     public void init(Context context){ //Http call (Client-Id,Sdk-Id)  // run all function on success or
         setContextApp(context);
         getDeviceUniqueId(context);
         getBuildVersion(context);
         getIPAddress(context);
         getCompanyName();
         getOSName();
         getApplicationId(context);
         getAppFirstInstalledDate(context);
//       alium.getCurrentHostname(this.getApplicationContext());
         getTimeZone();
         getUserAgent();
         getScreenSize(context);
         getVisitorID(context);
         getNetworkProvider(context);
         getGeoLocation(context);
//         getData();

         ///////////////////////------------------FIREBASE CODE--------------/////////////////////////////////////////
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

             NotificationChannel channel =
                     new NotificationChannel("MyNotifications", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);

             NotificationManager manager = getContextApp().getSystemService(NotificationManager.class);
             manager.createNotificationChannel(channel);
         }

         FirebaseMessaging.getInstance().subscribeToTopic("general")
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         String msg = "Successfully send";
                         if (!task.isSuccessful()) {
                             msg = "Send failed..!!";
                         }

                       Toast.makeText(getContextApp(), msg, Toast.LENGTH_SHORT).show();
                     }
         });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                       fcm = Objects.requireNonNull(task.getResult()).getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FCM Tag-----------", fcm);
                        Toast.makeText(getContextApp(), fcm, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")
    public void getDeviceUniqueId(Context context) {

        int myversion = 0;

        myversion = Build.VERSION.SDK_INT;
        if (myversion < 23) {
            TelephonyManager mngr = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            did = mngr.getDeviceId();

            if (did != null && !did.trim().isEmpty()) {

                if (did.length() <= 16 || did.length() >= 40) {
                    Log.d(ID_TAG, "Device ID length: " + did.length());
                } else {
                    Log.d(ID_TAG, "Device ID count is less than 16  or more than 40 : " + did);
                }
                Log.d(ID_TAG, "Device ID : " + did);
            }
        } else {
            did = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            if (did != null && !did.trim().isEmpty()) {
                if (did.length() <= 16 || did.length() >= 40) {
                    Log.d(ID_TAG, "Android ID length: " + did.length());
                } else {

//                    Log.d(ID_TAG, "Device ID count is less than 16  or more than 40 : " + myID);
                    Log.d(ID_TAG, "Android ID length not less ---------: " + did.length());
                }
                Log.d(ID_TAG, "Android ID: " + did);


            }
        }
    }


    public void getBuildVersion(Context context){
         try {
             bvrs = context.getPackageManager()
                     .getPackageInfo(context.getPackageName(), 0).versionName;
             //BuildConfig.VERSION_NAME;
             Log.d(ID_TAG, "Build Version length name: -------------------------------------------" + bvrs);
         }catch(Exception e){
             Log.d("Error Message","Error :"+e);
        }

//        boolean isASCII = true;
       /* if(versionName != null && !versionName.trim().isEmpty()) {
            for (int i = 0; i < versionName.length(); i++) {
                Log.d(ID_TAG, "Build Version length: -------------------------------------------" + versionName.length());
                int c = versionName.charAt(i);
                Log.d(ID_TAG, "Build Version length cccccc: -------------------------------------------" + c);
                if (c > 127) {
                    Log.d(ID_TAG, "Build Version length cccccc: -------------------------------------------" + c);
                    Log.d(ID_TAG, "Build Version length: " + versionName.length());
                    Log.d(ID_TAG, "Build Version is  : " + versionName);
//                    return false;
                }
            }
//            return true;


            *//*for (int i = 0; i < versionName.length(); i++) {
                int c = versionName.charAt(i);
                if (c > 0x7F) {
                    isASCII = false;

                    Log.d(ID_TAG, "Build Version length: " + versionName.length());
                    Log.d(ID_TAG, "Build Version is  : " + versionName);
                    break;
                }
            }*//*
            }else{

                Log.d(ID_TAG, "Build Version is  : " + versionName);
                Log.d(ID_TAG, "Build Version length not around 127 ---------: " +  versionName.length());
            }*/
    }


    public void getScreenSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        scrnsz = "Height :" +deviceHeight +" "+ "Width :"+deviceWidth;

        Log.d("Screen Size px"," "+scrnsz);

    }

    public void getOSName() {

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    orgs = field.getName();
                    Log.d("Android OsName:", orgs);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /////////////////////////--------------------Permissions Based Methods-------------/////////////////////

    public void getGeoLocation(Float [] string){

        gloc = string ;
        Log.d("Geo Location :","Latitude------------ :: "+ gloc[0] +" Longitude ------------ :: "+ gloc[1]);

    }

    public void getState(String state){
        st = state;
        Log.d("Geo Location :","Current State ----------------------------:" +st);
    }

    public void getCity(String city){
        ct = city;
        Log.d("Geo Location :","Locality/City ----------------------------:" +ct);
    }


    public void getCountry(String country){
        ctry = country;
         Log.d("Geo Location :"," Current Country ----------------------------:" +ctry);
    }

    public void getRegion(String region){
        rgn = region;
        Log.d("Geo Location :"," Current Region ----------------------------:" +rgn);
    }

    public void getNetworkProvider(Context context){

        TelephonyManager tm =
                (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        ntwp = tm.getNetworkOperatorName();
        Log.d("Network Provider : ","Net Provider :"+ntwp);

    }

    ///////////////////////////-----------------------//////////////////

    public void getSession(String sessionId){

         ssn = sessionId;
        Log.d("Session : ","SessionId :"+ssn);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void getApplicationId(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            aId = String.valueOf(info.versionCode);
//            aId = String.valueOf(context.getPackageManager()
//                    .getPackageInfo(context.getPackageName(), 0).getLongVersionCode());
            //BuildConfig.VERSION_NAME;
            Log.d(ID_TAG, "Build Version Codee: -------------------------------------------" + aId);
        }catch(Exception e){
            Log.d("Error Message","Error :"+e);
        }


//        aId = String.valueOf(BuildConfig.VERSION_CODE) ;
//        Log.d("Application Id:", aId);

    }

    public void getAppFirstInstalledDate(Context context) {

        PackageManager packageManager = context.getPackageManager();
        long installTimeInMilliseconds; // install time is conveniently provided in milliseconds
        Date installDate = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            installTimeInMilliseconds = packageInfo.firstInstallTime;
            aitd = MiscUtilities.getDate(installTimeInMilliseconds, "MM/dd/yyyy hh:mm:ss");
            Log.d("Date ","AppFirstInstalledDate---------------" + aitd);
        } catch (PackageManager.NameNotFoundException e) {
            // an error occurred, so display the Unix epoch
            installDate = new Date(0);
            aitd = installDate.toString();
        }

    }


    public void getIPAddress(Context context) {

        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        try {
            uia = InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .getHostAddress();

            Log.d("IP_ADDRESS", "IP-Address  -----------------:" + uia);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public synchronized static void getVisitorID(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);

            Log.d("Visitor_Id :", "Generated Visitor_Id -------------: ( " + uniqueID + " )"  );
            if (uniqueID == null) {

                uniqueID = UUID.randomUUID().toString();
                vstid = uniqueID;
                Log.d("Visitor_Id :", "Generated Visitor_Id: ( " + uniqueID + " )"  );
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();

            }
        }
    }


    public void getUserAgent(){
        ua = System.getProperty("http.agent");
        Log.d("Tag","User Agent---------------------- : "+ua);
    }

    public void getCompanyName() {

        cmp = Build.MANUFACTURER;
        Log.d("Company Name", "Build.MANUFACTURER -----------------:" + cmp);
        String model = Build.MODEL;
        Log.d("Company Name", "Build.MODEL -----------------:" + model);
        String device = Build.DEVICE;
        Log.d("Company Name", "Build.DEVICE -----------------:" + device);
        String brand = Build.BRAND;
        Log.d("Company Name", " Build.BRAND -----------------:" + brand);

    }


   public void getCurrentHostname(Context context)  {

   }


   public void getTimeZone(){

       Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
       TimeZone zone = calendar.getTimeZone();
       String timeZone = zone.getID();

       TimeZone tzs = TimeZone.getDefault();
       Integer tzz   = (int) System.currentTimeMillis();
     //  Integer tzz   = (int)calendar.getTimeInMillis();

       tz = tzz;

//       String gmt1=TimeZone.getTimeZone(tzs.getID()).getDisplayName(false,TimeZone.SHORT);
//       String gmt2=TimeZone.getTimeZone(tzs.getID()).getDisplayName(false,TimeZone.LONG);

//        tz = gmt1 +" "+timeZone;

//       Log.d("Tag","TimeZone---------------------- : "+gmt1+"\t"+gmt2);
       Log.d("Tag","TimeZone---------------------- : "+tz);

   }

    @SuppressLint("MissingPermission")
    private void getGeoLocation(Context contexts) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(contexts, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(contexts.getApplicationContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                  ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, 44);

                    locationEnabled();

            }

        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Location location = task.getResult();
                    if (location != null) {
                        try {

                            Geocoder geocoder = new Geocoder(getContextApp().getApplicationContext(), Locale.getDefault());
                            List<Address> address = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            float latitude = Float.parseFloat(String.valueOf((address.get(0).getLatitude())));
                            float longitude = Float.parseFloat(String.valueOf((address.get(0).getLongitude())));
//
//                            float latitude = (float)(address.get(0).getLatitude());
//                            float longitude = (float)(address.get(0).getLongitude());

                            Log.d("Geo Location :", "Longitude ----------: " + address.get(0).getLatitude()+" Longitude------------"+address.get(0).getLongitude());
//                            Float longitude = (float)(address.get(0).getLongitude());

                            geolocation = new Float[]{latitude, longitude};
                            Log.d("Geo Location :", "Float ----------:" + geolocation[0]+"--------------"+geolocation[1]);
                            getGeoLocation(geolocation);
                            getCountry(address.get(0).getCountryName());
                            getCity(address.get(0).getLocality());
                            getState(address.get(0).getAdminArea());
                            getRegion(address.get(0).getSubAdminArea());
//                            getData();
                            Log.d("Geo Location :", "Address ----------------------------:" + address.get(0).getAddressLine(0));


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

    }

    // Post Request For JSONObject
    /*public void getData()  {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        JSONObject obj = new JSONObject();
        String url = "https://mt8wslo0ml.execute-api.ap-south-1.amazonaws.com/default/logger-LoggerFunction-DF5DMWQXW20A";
        try {
            //input your API parameters
            obj.put("dim",dim);
            obj.put("did",did);
            obj.put("bvrs",bvrs);
            obj.put("pth",pth);
            obj.put("scrnsz",scrnsz);
            obj.put("st",st);
            obj.put("ct",ct);
            obj.put("ctry",ctry);
            obj.put("rgn",rgn);
            obj.put("ntwp",ntwp);
            obj.put("session",ssn);
            obj.put("tsls",tsls);
            obj.put("aId",aId);
            obj.put("aitd",aitd);
            obj.put("hnm",hnm);
            obj.put("uia",uia);
            obj.put("vstid",vstid);
            obj.put("ua",ua);
            obj.put("cmp",cmp);
            obj.put("tz",tz);
            obj.put("event",evnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       //resultTextView.setText("String Response : "+ response.toString());
                        Log.d("JSONObject  ","Inside getData ---- onResponseMethod---------2222222222---------:");
                        Log.d("Response :","Response--------------------"+response.toString());
                        Toast.makeText(context.getApplicationContext(), "I am OK !" + response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                resultTextView.setText("Error getting response");
                Log.d("JSONObject  ","Inside getData ---- onErrorResponse---------333333333333---------:"+error);
                Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }*/


    // Get Request For JSONObject
   /* public void getData() {

        Log.d("JSONObject  ", "Inside getData Method------------------:");
        final String url = "https://mt8wslo0ml.execute-api.ap-south-1.amazonaws.com/default/logger-LoggerFunction-DF5DMWQXW20A";

        new Thread() {
            @Override
            public void run() {
                try {
                    try {
                        Log.d("JSONObject  ", "Inside getData Method---------111111---------:");
                        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                        //  String url = "https://drrdh6hfj6.execute-api.ap-south-1.amazonaws.com/Prod/log";//getResources().getString(R.string.url);

                        JSONObject obj = new JSONObject();
                        obj.put("dim", dim);
                        obj.put("did", did);
                        obj.put("bvrs", bvrs);
                        obj.put("pth", pth);
                        obj.put("scrnsz", scrnsz);
                        obj.put("st", st);
                        obj.put("ct", ct);
                        obj.put("ctry", ctry);
                        obj.put("rgn", rgn);
                        obj.put("ntwp", ntwp);
                        obj.put("session", ssn);
                        obj.put("tsls", tsls);
                        obj.put("aId", aId);
                        obj.put("aitd", aitd);
                        obj.put("hnm", hnm);
                        obj.put("uia", uia);
                        obj.put("vstid", vstid);
                        obj.put("ua", ua);
                        obj.put("cmp", cmp);
                        obj.put("tz", tz);
                        obj.put("event", evnt);


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("JSONObject  ", "Inside getData ---- onResponseMethod---------2222222222---------:");
                                Log.d("Response :", "Response--------------------" + response.toString());
                                Toast.makeText(context.getApplicationContext(), "I am OK !" + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSONObject  ", "Inside getData ---- onErrorResponse---------333333333333---------:" + error);
                                Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }

                        });

                        Log.d("JSONObject  ", "Inside getData  End's -------------4444444444---------:");
                        requestQueue.add(jsonObjectRequest);
                        Log.d("JSONObject  ", "Inside getData  End's ----------55555555555---------:" + requestQueue.add(jsonObjectRequest));

                    } catch (Exception e) {
                        Log.d("JSONObject  ", "Inside getData  End's ----------Error ---------:");
                        e.printStackTrace();
                        Log.d("JSONObject  ", "Inside getData  End's ----------Error ---------:" + e);
                    }
*//*
                       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {

                               Log.d("JSONObject  ", "Inside getData ---- onResponseMethod---------2222222222---------:");
                               Log.d("Response :", "Response--------------------" + response.toString());
                               Toast.makeText(context.getApplicationContext(), "I am OK !" + response.toString(), Toast.LENGTH_LONG).show();
                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               Log.d("JSONObject  ", "Inside getData ---- onErrorResponse---------333333333333---------:" + error);
                               Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                           }
                       }) {

                           *//**//**
                     * Passing some request headers
                     *//**//*
                           @Override
                           public Map<String, String> getHeaders() throws AuthFailureError {
                               HashMap<String, String> headers = new HashMap<>();
                               Log.d("JSONObject  ", "Inside getData ---- getHeaders---------44444444444444---------:");
                               headers.put("Content-Type", "application/json");
//                               headers.put("key", "Value");
                               return headers;
                           }
                       };*//*
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
*/



   public void getData()  {

       new Thread() {
           @Override
           public void run() {
               try {
                   //Your code goes here
           // json formatted data
           String json = "{" +
                   "\"dim\": " + Arrays.toString(dim) + "," +
                   "\"did\": " + did + "," +
                   "\"bvrs\": " + bvrs + "," +
                   "\"pth\": " + pth + "," +
                   "\"scrnsz\": " + scrnsz + "," +
                   "\"st\": " + st + "," +
                   "\"ct\": " + ct + "," +
                   "\"ctry\": " + ctry + "," +
                   "\"rgn\": " + rgn + "," +
                   "\"ntwp\": " + ntwp + "," +
                   "\"ssn\": " + ssn + "," +
                   "\"tsls\": " + tsls + "," +
                   "\"aId\": " + aId + "," +
                   "\"aitd\": " + aitd + "," +
                   "\"hnm\": " + hnm + "," +
                   "\"uia\": " + uia + "," +
                   "\"vstid\": " + vstid + "," +
                   "\"ua\": " + ua + "," +
                   "\"cmp\": " + cmp + "," +
                   "\"tz\": " + tz + "," +
                   "\"evnt\": " + evnt + "," +
                   "\"fcm\": " + fcm + "," +
                   "}";

           // json request body
           RequestBody body = RequestBody.create(json,  MediaType.parse("application/json; charset=utf-8"));

           Request request = new Request.Builder()
                   .url("https://drrdh6hfj6.execute-api.ap-south-1.amazonaws.com/Prod/log")
                   .addHeader("content-type", "application/json")
//                   .addHeader("parameters",json)
                   .post(body)
                   .build();

           try  {

               Response response = httpClient.newCall(request).execute();
               if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

               Headers responseHeaders = response.headers();
               for (int i = 0; i < responseHeaders.size(); i++) {
                   Log.d("Header Response","Response-----------" +responseHeaders.name(i) + ": " + responseHeaders.value(i));
               }
               // Get response body
//               System.out.println(response.body().string());
               Log.d("Error :", "Response  ---------------" +response.body());
           }catch(Exception e){
               Log.d("Error :", "Error ---------------" + e);
           }

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }.start();

   }


       /*OkHttpClient client = new OkHttpClient().newBuilder()
               .build();
       MediaType mediaType = MediaType.parse("application/json");
       RequestBody body = RequestBody.create(mediaType, "{\r\n    \"bwsn\": \"Chrome\",\r\n    \"bwsr\": \"1080 X 1920\",\r\n    \"bwsvrs\": \"84.0.4147.105\",\r\n    \"cmp\": \"TCZ\",\r\n    \"ct\": \"\",\r\n    \"ctry\": \"\",\r\n    \"dim\": [\r\n        \"banner-btn-1\",\r\n        \"a\",\r\n        \"\",\r\n        \"Shop Now!\"\r\n    ],\r\n    \"evnt\": \"click\",\r\n    \"fcm\": \"e8KGOZ0BtZb0OqU0dC0VIv:APA91bGaRE2ejXLN3BeM7u90Hb9kLxWCxMgRM1tRhQmrxOKxT-1qhVF5miuqevuFztYABx7Uq_eDUyrXYNbzla97Q-Nda4uBsMvzVGOFZwUeYQ4TyS0sHkk_9leaYz266MkqHSSAZEHE\",\r\n    \"gloc\": [],\r\n    \"hnm\": \"https://dev-webpush.alium.co.in\",\r\n    \"ntwp\": \"\",\r\n    \"orgs\": \"Windows OS\",\r\n    \"pth\": \"/\",\r\n    \"rgn\": \"\",\r\n    \"ssn\": \"ea7ad33f-5dfc-4b2b-b6c9-d2a4c980f42a\",\r\n    \"st\": \"\",\r\n    \"tsls\": \"\",\r\n    \"tz\": 1596525338824,\r\n    \"ua\": \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36 Edg/84.0.522.52\"\r\n}");
      try {
          Request request = new Request.Builder()
                  .url("https://mt8wslo0ml.execute-api.ap-south-1.amazonaws.com/default/logger-LoggerFunction-DF5DMWQXW20A")
                  .method("POST", body)
                  //    .addHeader("access-control-allow-credentials", " true")
                  //   .addHeader("access-control-allow-headers", " Content-Type")
                  .addHeader("content-type", "application/json")
                  .build();
          Response response = client.newCall(request).execute();
          Log.d("Response :", "Response ---------------" + response);
      }catch(Exception e){
          Log.d("Error :", "Error ---------------" + e);
      }*/




        /*public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

        String bowlingJson(String player1, String player2) {
            return "{'winCondition':'HIGH_SCORE',"
                    + "'name':'Bowling',"
                    + "'round':4,"
                    + "'lastSaved':1367702411696,"
                    + "'dateStarted':1367702378785,"
                    + "'players':["
                    + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                    + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                    + "]}";
        }*/


    private void locationEnabled() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(activity)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                   activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }


  /*  void getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }*/
/*

    public void onLocationChanged(Location location) {

        try {

        Geocoder geocoder = new Geocoder(getContextApp().getApplicationContext(), Locale.getDefault());
        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

        Log.d("Geo Location :","Latitude ----------------------------:" +address.get(0).getLatitude());
        String latitude  = String.valueOf(address.get(0).getLatitude());

        Log.d("Geo Location :","Longitude ----------------------------:" +address.get(0).getLongitude());
        String longitude  = String.valueOf(address.get(0).getLongitude());

        geolocation = new String[]{latitude,longitude};
        getGeoLocation(geolocation);
        getCountry(address.get(0).getCountryName());
        getCity(address.get(0).getLocality());
        getState(address.get(0).getAdminArea());
        getRegion(address.get(0).getSubAdminArea());
        getData();
        Log.d("Geo Location :","Address ----------------------------:" +address.get(0).getAddressLine(0));

    } catch (Exception e) {
    }
    }
*/



}







