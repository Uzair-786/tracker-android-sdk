package org.alium.trackerlibrary;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import android.app.Notification;
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

@SuppressLint({"MissingFirebaseInstanceTokenRefresh","NewApi"})
public class MessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());

    }

    public void showNotification(String title, String remoteMessage) {

        Notification notification = new NotificationCompat.Builder(this.getApplicationContext(),"MyNotifications")
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle(title)
                .setContentText(remoteMessage)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,notification);
    }

}
