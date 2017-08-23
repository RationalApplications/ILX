package xyz.ratapp.ilx.controllers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telecom.Call;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.ExecutionException;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.LaunchActivity;
import xyz.ratapp.ilx.ui.activities.MainActivity;

/**
 * Created by Олег on 20.08.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("MyTag", remoteMessage.getData().toString());

        String iconBig = remoteMessage.getData().get("icon_big");
        String iconPreview = remoteMessage.getData().get("icon_preview");
        String type= remoteMessage.getData().get("type");
        String title= remoteMessage.getData().get("title");
        String message= remoteMessage.getData().get("message");
        String eventTime= remoteMessage.getData().get("event_time");


        sendNotification(iconBig, iconPreview, type, title, message, eventTime);
    }

    private void sendNotification(String iconBig, String iconPreview, String type, String title, String message, String eventTime) {
        Bitmap theBitmap = null;
        try {
            theBitmap = Glide.
                    with(this).
                    load(iconBig).
                    asBitmap().
                    into(100, 100). // Width and height
                    get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
    }

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_custom);

        remoteView.setImageViewBitmap(R.id.iv_notif_icon, theBitmap);
        remoteView.setTextViewText(R.id.tv_title, title);
        remoteView.setTextViewText(R.id.tv_message, message);
        Time t = new Time();
        t.set(Long.parseLong(eventTime));
        remoteView.setTextViewText(R.id.tv_time, t.hour + ":" + t.minute    );
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setCustomContentView(remoteView);
/*Icon.createWithBitmap(yourDownloadedBitmap)*/

        Intent resultIntent = null;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        resultIntent = new Intent(this, LaunchActivity.class);
        resultIntent.putExtra("type", type);
        stackBuilder.addParentStack(LaunchActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
