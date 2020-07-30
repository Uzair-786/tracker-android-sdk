package org.alium.trackerlibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.core.app.ActivityCompat;

import com.google.android.datatransport.BuildConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
/**
 * @author UzairWani
 *
 * This class gets all the data from the application for tracking the user activities
 * on an integerated application
 */

public class Alium {

    private Context context;
    private Activity activity = (Activity)context;

    public Alium (Activity context){
        this.activity = context;
    }

  /*  public Activity setInstance(Activity activity) {
      return this.activity = (Activity)context;
    }

    public Activity getInstance(Activity activity) {
        if(activity == null){
            Log.d("Activity :","Activity is null");
           return activity = setInstance(null);
        }else{
            Log.d("Activity :","Activity ---------------------");
            return activity;
        }

    }*/

    private final static String ID_TAG = "ID";

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    private String[] geolocation;

    private FusedLocationProviderClient fusedLocationProviderClient;


    public Context setContextApp(Context context) {
       return this.context = context;
    }

    public Context getContextApp() {
        return context;
    }


    private String[] dim = null;               //Element on which action is done.
    private String did = null;                 //device_id || android_id
    private String bvrs = null;                //build_version
    private String pth = null;                 //screen/path/route
    private String scrnsz = null;              //screen_size
    private String orgs = null;                //operating_system
    private String [] gloc = null;             //geo_location       --------- Based on App Permissions
    private String st = null;                  //state              --------- Based on App Permissions
    private String ct = null;                  //city               --------- Based on App Permissions
    private String ctry = null;                //country            --------- Based on App Permissions
    private String rgn = null;                 //region             --------- Based on App Permissions
    private String ntwp = null;                //network provider   --------- Based on App Permissions
    private String ssn = null;                 //session
    private String tsls = null;                //time since last login/session
    private String aId = null;                 //app_id
    private String aitd =null;                 //app install date
    private String hnm = null;                 //current_hostname
    private String uia = null;                 //user ip_address
    private static String vstid ;              //visitor id
    private String ua = null;                  //user_agent
    private String cmp = null;                 //company_name
    private String tz = null;                  //timezone
    private String evnt = null;                //event_name



     @RequiresApi(api = Build.VERSION_CODES.O)
     public void init(Context context){
         setContextApp(context);
         getDeviceUniqueId(context);
         getBuildVersion();
         getIPAddress(context);
         getCompanyName();
         getOSName();
         getApplicationId();
         getAppFirstInstalledDate(context);
//       alium.getCurrentHostname(this.getApplicationContext());
         getTimeZone();
         getUserAgent();
         getScreenSize(context);
         getVisitorID(context);
         getNetworkProvider(context);
         getGeoLocation(context);

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


    public void getBuildVersion() {
        bvrs = BuildConfig.VERSION_NAME;
        Log.d(ID_TAG, "Build Version length name: -------------------------------------------" + bvrs);

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

    public void getGeoLocation(String [] string){

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


    public void getApplicationId() {

        aId = String.valueOf(BuildConfig.VERSION_CODE) ;
        Log.d("Application Id:", aId);

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

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
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

    public synchronized static String getVisitorID(Context context) {
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
        return uniqueID;
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
       String gmt1=TimeZone.getTimeZone(tzs.getID())
               .getDisplayName(false,TimeZone.SHORT);
       String gmt2=TimeZone.getTimeZone(tzs.getID())
               .getDisplayName(false,TimeZone.LONG);

        tz = gmt1 +" "+timeZone;

       Log.d("Tag","TimeZone---------------------- : "+gmt1+"\t"+gmt2);
       Log.d("Tag","TimeZone---------------------- : "+tz);

   }

    @SuppressLint("MissingPermission")
    private void getGeoLocation(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if(location != null){
                    try {

                        Geocoder geocoder = new Geocoder(getContextApp().getApplicationContext(), Locale.getDefault());


                        List<Address> address = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );


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

                        Log.d("Geo Location :","Address ----------------------------:" +address.get(0).getAddressLine(0));


                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

        });
        } else {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }


}




