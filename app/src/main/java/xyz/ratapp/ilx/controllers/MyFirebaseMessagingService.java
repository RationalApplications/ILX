package xyz.ratapp.ilx.controllers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.telecom.Call;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Date;
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
        String mdKey= remoteMessage.getData().get("md_key");


        sendNotification(mdKey, iconBig, iconPreview, type, title, message, eventTime);
    }

    private void sendNotification(String mdKey, String iconBig, String iconPreview, String type, String title, String message, String eventTime) {
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

        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), theBitmap);
        bitmapDrawable.setCornerRadius(30f);

        remoteView.setImageViewBitmap(R.id.iv_notif_icon,  getCroppedBitmap(theBitmap));
        remoteView.setTextViewText(R.id.tv_title, title);
        remoteView.setTextViewText(R.id.tv_message, message);
        Date date = Calendar.getInstance().getTime();
        remoteView.setTextViewText(R.id.tv_time, date.getHours() + ":" + date.getMinutes());

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(theBitmap)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                        .setPriority(Notification.VISIBILITY_PUBLIC)
                        .setTicker(title, remoteView)
                        .setCustomHeadsUpContentView(remoteView)
                        .setCustomContentView(remoteView);
/*Icon.createWithBitmap(yourDownloadedBitmap)*/
        Intent resultIntent;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        resultIntent = new Intent(this, LaunchActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("type", type);
        resultIntent.putExtra("md_key", mdKey);
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

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
