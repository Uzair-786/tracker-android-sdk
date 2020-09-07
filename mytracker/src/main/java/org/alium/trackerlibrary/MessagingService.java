package org.alium.trackerlibrary;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
/**
 * @author UzairWani
 *
 * This is a firebase Messaging service
 */


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {

    @SuppressLint("NewApi")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("MessagingService","Inside onMessageReceived ----------");
        super.onMessageReceived(remoteMessage);
        final RemoteMessage remotesMessage = remoteMessage;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MessagingService","Inside onMessageReceived run()-----111111111111111-----");
                showNotification(Objects.requireNonNull(remotesMessage.getNotification()).getTitle(),remotesMessage.getNotification().getBody());
                Log.d("MessagingService","Inside onMessageReceived -----222222222222222-----");
            }
        }).start();


    }

    public void showNotification(String title, String remoteMessage) {
        Log.d("MessagingService","Inside showNotification -----1111111111111-----");
        Notification notification = new NotificationCompat.Builder(this.getApplicationContext(),"MyNotifications")
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle(title)
                .setContentText(remoteMessage)
//                .setPriority(NotificationManagerCompat.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .build();
        Log.d("MessagingService","Inside showNotification -----2222222222222-----");

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        Log.d("MessagingService","Inside showNotification -----3333333333333-----");
        manager.notify(999,notification);
        Log.d("MessagingService","Inside showNotification -----4444444444444-----");
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }


    /* @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

}
