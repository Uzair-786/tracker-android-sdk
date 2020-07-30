package org.alium.trackerlibrary;

import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.RequiresApi;
import java.util.HashMap;
import java.util.Map;

/**
 * @author UzairWani
 *
 * This is a Action Tracker for tracking events that are forwarded from the application
 */

public class Tracker {

    private static final String TAGS = "Trackers ";
    int counter = 0;
    static Map<String,Integer> map =new HashMap();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickTracker(View view) {
        Log.d(TAGS, "Inside On ClickTracker()");
       view.setOnTouchListener(new View.OnTouchListener() {
           @RequiresApi(api = Build.VERSION_CODES.N)
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {

               if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                   String viewName = view.getResources().getResourceEntryName(view.getId());
                   Log.d(TAGS, "Item clicked :(name):(" + viewName + ")");

                   clickCounter(viewName,counter);

                   String widgetName = view.getAccessibilityClassName().toString();
                   String[] className = widgetName.split("\\.");
                   Log.d(TAGS, "Widget Class Name : (v): (" + className[2] + ")");

                   return false;
               }

               return false;
           }
       });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickCounter(String viewName,Integer counter){

        if(!map.containsKey(viewName)){
            map.put(viewName,counter);
        }

        for(String key : map.keySet()){
            if(viewName.equals(key)){
             Integer count = map.get(viewName);
                count++;
                map.replace(viewName,count) ;
            }
        }

        Log.d(TAGS, "Map of Touched Items : (v): ("+map+")");

    }

}
