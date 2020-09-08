package org.alium.trackerlibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.alium.trackerlibrary.init.Init;
import org.alium.trackerlibrary.init.InitRetrofitClient;
import org.alium.trackerlibrary.init.data.ResponseClass;
import org.alium.trackerlibrary.retrofit.AliumData;
import org.alium.trackerlibrary.retrofit.RetrofitClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author UzairWani
 *
 * This class gets all the data from the application for tracking the user activities
 * on an integerated application
 */

@SuppressLint("NewApi")
public class Alium extends Application {

    private Context context;
    public Activity activity ;

    private static final String EXTRA_PATH = "pth";

    public Alium() {
    }

    public Alium (Activity context){
        this.activity = context;
    }

    private final static String ID_TAG = "ID";
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private Float[] geolocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public Context setContextApp(Context context) {
       return this.context = context;
    }

    public Context getContextApp() {
        return context;
    }

    private static final String TAGS = "Trackers ";
    int counter = 0;
    static Map<String,Integer> map =new HashMap();
    ArrayList<HashMap<String,String>> dims =new  ArrayList<>();

    static HashMap<String,String> dimMapping =new HashMap();

//    private ArrayList<String> dim = new ArrayList<>() ;               //Element on which action is done. {"", "button", "FCM Token", "×"}
    private ArrayList<String> dim =new  ArrayList<>();
    private String did = "";                                          //device_id || android_id
    private String bvrs = "";                                         //build_version
    private String pth = ""; //"com.example.loginapp.MainActivity";         //screen/path/route
    private String scrnsz = "";                                       //screen_size
    private String orgs = "";                                         //operating_system
    private Float[] gloc = new Float[2];                              //geo_location       --------- Based on App Permissions
    private String st = "";                                           //state              --------- Based on App Permissions
    private String ct = "";                                           //city               --------- Based on App Permissions
    private String ctry = "";                                         //country            --------- Based on App Permissions
    private String rgn = "";                                          //region             --------- Based on App Permissions
    private String ntwp = "";                                         //network provider   --------- Based on App Permissions
    private String ssn = "sd4xg5s-44f5-54edf-65d65" ;                 //session
    private String tsls = "01:50 pm";                                 //time since last login/session
    private String aId = "";                                          //app_id
    private String aitd = "";                                         //app install date
    private String hnm = "My Local Host";                             //current_hostname
    private String uia = "";                                          //user ip_address
    private String vstid="" ;                                         //visitor id
    private String ua = "";                                           //user_agent
    private String cmp = "";                                          //company_name
    private Long tz = 0L;                                             //timezone
    private String evnt = "click";                                    //event_name
    private String fcm = "";                                          //FCM token
    private String platform = "ANDROID";                              //Default Lead Message always -> "Android"

    ///////////// Tracker Method //////////////////

    public void onClickTracker(View view) {
        Log.d(TAGS, "Inside On ClickTracker()");
        view.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    String viewName = view.getResources().getResourceEntryName(view.getId());
                    Log.d(TAGS, "Item clicked :(name):(" + viewName + ")");

//                    clickCounter(viewName,counter);

                    String widgetName = view.getAccessibilityClassName().toString();
                    String[] className = widgetName.split("\\.");
                    Log.d(TAGS, "Widget Class Name : (v): (" + className[2] + ")");

                    if(viewName.equals("btnLogin")){
                        postData();
                    }

                    dimMapping = dims.get(0);
                    dimMapping.replace("dimAction",viewName);
                    dim.add(0,dimMapping.get("dimAction"));
                    Log.d(TAGS, "Dim Mapping -----1111------" + dimMapping );

                    dimMapping = dims.get(1);
                    dimMapping.replace("displayName","widgetName");
                    dimMapping.replace("dimAction",className[2]);
                    dim.add(1,dimMapping.get("displayName"));
                    dim.add(2,dimMapping.get("dimAction"));
                    Log.d(TAGS, "Dim Mapping -----2222------" + dimMapping );

                    dimMapping = dims.get(2);
                    dimMapping.replace("displayName",Build.DEVICE);
                    dimMapping.replace("dimAction","Android");
                    dim.add(3,dimMapping.get("displayName"));
                    dim.add(4,dimMapping.get("dimAction"));
                    Log.d(TAGS, "Dim Mapping -----3333------" + dimMapping );

                    dimMapping = dims.get(3);
                    dimMapping.replace("displayName","4000");
                    dimMapping.replace("dimAction","click");
                    dim.add(5,dimMapping.get("displayName"));
                    dim.add(6,dimMapping.get("dimAction"));
                    Log.d(TAGS, "Dim Mapping -----4444------" + dimMapping );

                    dimMapping = dims.get(4);
                    dimMapping.replace("displayName",className[2]);
                    dimMapping.replace("dimAction","click");
                    dim.add(7,dimMapping.get("displayName"));
                    dim.add(8,dimMapping.get("dimAction"));
//                    dim.add(4,dimMapping);
                    Log.d(TAGS, "Dim Mapping -----5555------" + dimMapping );

                    Log.d(TAGS, "Dim Mapping -----------" + dim);

                   /* if(className[2].equals("Button")){
                        postData();
                    }*/

                    return false;
                }

                return false;
            }
        });

    }

   /* public void clickCounter(String viewName,Integer counter){

        if(!map.containsKey(viewName)){
            map.put(viewName,counter);
        }

        for(String key : map.keySet()){
            if(viewName.equals(key)){
                Integer count = map.get(viewName);
                count++;
                map.replace(viewName,count) ;
                if(dim.size() < 1) {
                    dim.add(map.toString());
                }else{
                    dim.set(0,map.toString());
                }
                Log.d(ID_TAG, " Dim: -------------------------------------------" +dim);
            }
        }

        Log.d(TAGS, "Map of Touched Items : (v): ("+map+")");

    }*/

     public void initMethod(Context context){  //Http call (Client-Id,Sdk-Id)  // run all function on success or
         Log.d("Alium","Inside init ----------");
         setContextApp(context);
         Log.d("Alium","Inside init -----1111111111-----");
         getPath();
         Log.d("Alium","Inside init -----2222222222-----");
         getDeviceUniqueId(context);
         Log.d("Alium","Inside init -----3333333333-----");
         getBuildVersion(context);
         Log.d("Alium","Inside init -----4444444444-----");
         getIPAddress(context);
         Log.d("Alium","Inside init -----5555555555-----");
         getCompanyName();
         Log.d("Alium","Inside init -----666666666-----");
         getOSName();
         Log.d("Alium","Inside init -----777777777-----");
         getApplicationId(context);
         Log.d("Alium","Inside init -----888888888-----");
         getAppFirstInstalledDate(context);
         Log.d("Alium","Inside init -----999999999-----");
//       alium.getCurrentHostname(this.getApplicationContext());
         getTimeZone();
         Log.d("Alium","Inside init -----10-10-10-10--");
         getUserAgent();
         Log.d("Alium","Inside init -----11-11-11-11--");
         getScreenSize(context);
         Log.d("Alium","Inside init ------12-12-12-12-");
         getVisitorID(context);
         Log.d("Alium","Inside init ------13-13-13-13-");
         getNetworkProvider(context);
         Log.d("Alium","Inside init -----14-14-14-14--");
         getGeoLocation(context);
         Log.d("Alium","Inside init -----15-15-15-15--");
         ///////////////////////------------------FIREBASE CODE--------------/////////////////////////////////////////
        startPowerSaverIntent(context);
         Log.d("Alium","Inside init [FIREBASE CODE]----------");

         // Retrieve my other app.
//         FirebaseApp app = FirebaseApp.getInstance("secondary");
        /* FirebaseOptions options = new FirebaseOptions.Builder()
                 .setApiKey("AIzaSyBU-S6u7wusyywPxb7f3WScKlkVtK_Fwvo")
                 .setProjectId("alium-ee7c7")
                 .setApplicationId("1:559777811749:android:10b753bf4b2b8484aa5ecc")
                 .setDatabaseUrl("https://alium-ee7c7.firebaseio.com")
                 .setStorageBucket("alium-ee7c7.appspot.com")
                 .build();

         FirebaseApp.initializeApp(context, options, "secondary");*/
        // Get the database for the other app.
         //FirebaseMessaging secondaryDatabase = FirebaseMessaging.getInstance(); //getInstance(app);

//         FirebaseApp.initializeApp(context);
//        turnOffDozeMode(context);

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             Log.d("Alium","Inside init [FIREBASE CODE]-----111111111-----");
             @SuppressLint("WrongConstant") NotificationChannel channel =
                     new NotificationChannel("MyNotifications", "MyNotification", NotificationManager.IMPORTANCE_MAX);
             Log.d("Alium","Inside init [FIREBASE CODE]-----22222222-----");
             NotificationManager manager = getContextApp().getSystemService(NotificationManager.class);
             Log.d("Alium","Inside init [FIREBASE CODE]------3333333----");
             manager.createNotificationChannel(channel);
             Log.d("Alium","Inside init [FIREBASE CODE]------44444444----");
         }

         FirebaseMessaging.getInstance().subscribeToTopic("general")
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         Log.d("Alium","Inside init [FIREBASE CODE]-----5555555-----");
                         String msg = "Successfully send";
                         if (!task.isSuccessful()) {
                             msg = "Send failed..!!";
                             Log.d("Alium","Inside init [FIREBASE CODE]----66666------"+msg);
                         }

                    //   Toast.makeText(getContextApp(), msg, Toast.LENGTH_SHORT).show();
                     }
         });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Log.d("Alium","Inside init [FIREBASE CODE]----777777777777------");
                        if (!task.isSuccessful()) {
                            Log.d("Alium","Inside init [FIREBASE CODE]-----8888888888-----");
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            Log.d("Alium","Inside init [FIREBASE CODE]------99999999----");
                            return;
                        }

                        // Get new Instance ID token
                       fcm = Objects.requireNonNull(task.getResult()).getToken();
                        Log.d("Alium","Inside init [FIREBASE CODE]---10-10-10-10----");
//                       dim.add(fcm);

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FCM Tag-----------", fcm);
                        Log.d("Alium","Inside init [FIREBASE CODE]---11-11-11-11----");
//                        Toast.makeText(getContextApp(), fcm, Toast.LENGTH_SHORT).show();
                    }
                });

     }

//    @SuppressLint("NewApi")
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

    public String getPath() {
        try {
            if (pth == null || pth.isEmpty()) {
                pth = activity.getIntent().getStringExtra(EXTRA_PATH);
                pth = pth == null ? ("") : (pth += "/");
                pth += activity.getClass();
                String[] splited = pth.split("\\s+");
                pth = splited[1];
                Log.d("Path  :: ","This is the activity path ------"+pth);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pth;
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

        gloc[0] = string[0] ;
        gloc[1] = string[1];

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

    public void getSession(String sessionId){

         ssn = sessionId;
        Log.d("Session : ","SessionId :"+ssn);
    }

    public void getApplicationId(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            aId = String.valueOf(info.versionCode);
            Log.d(ID_TAG, "Build Version Codee: -------------------------------------------" + aId);
        }catch(Exception e){
            Log.d("Error Message","Error :"+e);
        }

    }

    public void getAppFirstInstalledDate(Context context) {

        PackageManager packageManager = context.getPackageManager();
        long installTimeInMilliseconds; // install time is conveniently provided in milliseconds
        Date installDate = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            installTimeInMilliseconds = packageInfo.firstInstallTime;
            aitd = MiscUtilities.getDate(installTimeInMilliseconds, "dd-MM-yyyy HH:mm:ss");//"MM/dd/yyyy hh:mm:ss");
            Log.d("Date ","AppFirstInstalledDate---------------" + aitd);
        } catch (PackageManager.NameNotFoundException e) {
            // an error occurred, so display the Unix epoch
            installDate = new Date(0);
            aitd = installDate.toString();
            Log.d("Date ","AppFirstInstalledDate---------------" + aitd);
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

    public void getVisitorID(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            vstid = uniqueID;

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

//       Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//       TimeZone zone = calendar.getTimeZone();
//       String timeZone = zone.getID();
       TimeZone tzs = TimeZone.getDefault();
      //  tz = new Timestamp(System.currentTimeMillis()) ;//System.currentTimeMillis();
        tz =System.currentTimeMillis();
       Log.d("Tag","TimeZone---------------------- : "+tz);

   }

    @SuppressLint("MissingPermission")
    public void getGeoLocation(Context contexts) {

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
                    if (location != null) {
                        try {

                            Geocoder geocoder = new Geocoder(getContextApp().getApplicationContext(), Locale.getDefault());
                            List<Address> address = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1);

                            float latitude = Float.parseFloat(String.valueOf((address.get(0).getLatitude())));
                            float longitude = Float.parseFloat(String.valueOf((address.get(0).getLongitude())));

                            Log.d("Geo Location :", "Longitude ----------: " + address.get(0).getLatitude()+" Longitude------------"+address.get(0).getLongitude());

                            geolocation = new Float[]{latitude, longitude};
//                            Log.d("Geo Location :", "Float ----------:" + geolocation[0]+"--------------"+geolocation[1]);
                            getGeoLocation(geolocation);
                            getCountry(address.get(0).getCountryName());
                            getCity(address.get(0).getLocality());
                            getState(address.get(0).getAdminArea());
                            getRegion(address.get(0).getSubAdminArea());
//                            postData();
                            Log.d("Geo Location :", "Address ----------------------------:" + address.get(0).getAddressLine(0));
//                            Log.d(ID_TAG, " Dim: -------------------------------------------" +dim);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

    }

    public void locationEnabled() {
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

    public void postData() {

    try {

        JSONArray dimJsonArray = new JSONArray(dim);
        JSONArray glocJsonArray = new JSONArray(gloc);
        AliumData aliumData = new AliumData(dimJsonArray,did,bvrs,pth,scrnsz,orgs,glocJsonArray,st,ct,ctry,rgn,ntwp,ssn,tsls,aId,aitd,hnm,uia,vstid,ua,cmp,tz,evnt,fcm,platform) ;
        dim.clear();
        JSONObject json = aliumData.toJSON();

        Log.d("JSON ::", "Json Object ---------" + json);

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        Call<ResponseBody> call = RetrofitClient
                                    .getInstance()
                                    .getApiPost()
                                    .PostData(headerMap,json);

                call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(!response.isSuccessful()){
                        Log.d("Response ::", "Response is not Successful ---------" + response);
                    }else{
                        Log.d("Response ::", "Success response---------" + response);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Log.d("Error ::", "Inside Throwable -------" + t);

                }
            });

         }catch(Exception e){
            Log.d("Error ::", "Inside Exception -------" + e);
         }
    }

    /*public void turnOffDozeMode(Context context){  //you can use with or without passing context
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName)) // if you want to desable doze mode for this package
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else { // if you want to enable doze mode
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }
            context.startActivity(intent);
        }

    }*/

    public void startPowerSaverIntent(final Context context) {
        SharedPreferences settings = context.getSharedPreferences("ProtectedApps", Context.MODE_PRIVATE);
        boolean skipMessage = settings.getBoolean("skipProtectedAppCheck", false);
        if (!skipMessage) {
            final SharedPreferences.Editor editor = settings.edit();
            boolean foundCorrectIntent = false;
            for (final Intent intent : Constants.POWERMANAGER_INTENTS) {
                if (isCallable(context, intent)) {
                    foundCorrectIntent = true;
                    final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(activity.getApplicationContext());
                    dontShowAgain.setText("Do not show again");
                    dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            editor.putBoolean("skipProtectedAppCheck", isChecked);
                            editor.apply();
                        }
                    });

                    final CharSequence[] charSequence = new CharSequence[] {"Do not show again"};
                    String[] items = {"Do not show again"};
                    boolean[] checkedItems = {false};
//                    final boolean isChecked =false;
                     new AlertDialog.Builder(activity)
                            .setTitle("Enable "+context.getPackageManager().getApplicationLabel(context.getApplicationInfo())+" notifications always")
//                            .setMessage("Requires to enable App in the background")
//                            .setView(dontShowAgain)
                             .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                     editor.putBoolean("skipProtectedAppCheck", b);
                                     editor.apply();
                                 }

                             })
                         /*   .setSingleChoiceItems(charSequence, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editor.putBoolean("skipProtectedAppCheck", isChecked);
                                    editor.apply();
                                }
                            })*/
                            .setCancelable(false)
                            .setPositiveButton("Enable", new
                                    DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            activity.startActivity(intent);
                                        }
                                    })
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();

/*
                    new AlertDialog.Builder(context)
                            .setTitle(Build.MANUFACTURER + " Protected Apps")
                            .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", context.getString(R.string.project_id)))
                            .setView(dontShowAgain)
                            .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getContextApp().startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();*/
                    break;
                }
            }
            if (!foundCorrectIntent) {
                editor.putBoolean("skipProtectedAppCheck", true);
                editor.apply();
            }
        }
    }

    private static boolean isCallable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void init(String clientId, String sdkId, final Context contexts) {

        try {

            Init initObject = new Init(clientId,sdkId) ;

            Log.d("JSON ::", "Json Object ---------" + initObject.toString());

            Call<ResponseClass> call = InitRetrofitClient
                    .getInstance()
                    .getApiPost()
                    .PostData(initObject);

            call.enqueue(new Callback<ResponseClass>() {
                @Override
                public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                    if(!response.isSuccessful()){
                        Log.d("Response ::", "Response is not Successful (init)---------" + response.body());
                    }else{

                        dims =   response.body().getData().getDims().getDims();
                        Log.d("Response ::", "Dims Data (init)---------" + dims);
                        Log.d("Response ::", "Success response (init)---------" + response);
                        initMethod(contexts);
                    }
                }

                @Override
                public void onFailure(Call<ResponseClass> call, Throwable t) {
                    Log.d("Error ::", "Inside Throwable -------" + t);
                }
            });


        }catch(Exception e){
            Log.d("Error ::", "Inside Exception -------" + e);
        }
    }
}









