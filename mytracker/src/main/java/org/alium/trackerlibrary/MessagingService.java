package org.alium.trackerlibrary;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Build;

import androidx.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());

    }

    public void showNotification(String title, String remoteMessage) {

        Notification notification = new NotificationCompat.Builder(this.getApplicationContext(),"MyNotifications")
                .setSmallIcon(R.drawable.googleg_standard_color_18)
                .setContentTitle(title)
                .setContentText(remoteMessage)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,notification);
    }

}
