package ramvan.com.learnnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    private TextView mSimpleNotification;
    private TextView mSimpleLargeIconNotification;
    private TextView mSimpleLargeTextNotification;
    private TextView mCancelNotification;
    private TextView mCancelAllNotification;
    private TextView mOpenActivity;
    private TextView mOpenFragment;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mManager;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimpleNotification = (TextView) findViewById(R.id.simple_small_icon_notification);
        mSimpleNotification.setOnClickListener(this);

        mSimpleLargeIconNotification = (TextView) findViewById(R.id.simple_large_icon_notification);
        mSimpleLargeIconNotification.setOnClickListener(this);

        mSimpleLargeTextNotification = (TextView) findViewById(R.id.simple_large_text_notification);
        mSimpleLargeTextNotification.setOnClickListener(this);

        mCancelNotification = (TextView) findViewById(R.id.cancel_notification);
        mCancelNotification.setOnClickListener(this);

        mCancelAllNotification = (TextView) findViewById(R.id.cancel_all_notification);
        mCancelAllNotification.setOnClickListener(this);

        mOpenActivity = (TextView) findViewById(R.id.open_act_notification);
        mOpenActivity.setOnClickListener(this);

        mOpenFragment = (TextView) findViewById(R.id.open_fragment_notification);
        mOpenFragment.setOnClickListener(this);

        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this);
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.simple_small_icon_notification:
                simpleNotification();
                break;

            case R.id.simple_large_icon_notification:
                getLargeImage();
                break;

            case R.id.simple_large_text_notification:
                simpleBigTextNotification();
                break;

            case R.id.cancel_notification:
                // currently i am clearing notification with notificationId = 1. You can delete any notification using its id.
                mManager.cancel(1);
//              mManager.cancel(2);
//              mManager.cancel(3);
                break;

            case R.id.cancel_all_notification:
                mManager.cancelAll();
                break;

            case R.id.open_act_notification:
                openActivity();
                break;

            case R.id.open_fragment_notification:
                openFragment();
                break;
        }
    }

    /**
     * open an fragment when user tap on Notfication
     */
    private void openFragment() {
        mManager.cancelAll();
        simpleNotificationWithFragment();
    }

    /**
     * open an Activity when user tap on Notification.
     */
    private void openActivity() {
        mManager.cancelAll();
        simpleNotificationWithActivity();
    }

    /**
     * create the simple notification
     */
    private void simpleNotificationWithFragment() {
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Simple Notification");
        mBuilder.setContentText("I am Fragment Context text , you can change me.");
        int notificationId = 1;
        Intent mNotificationIntent  = new Intent(MainActivity.this , NewActivity.class);
        mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        sharedpreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Source", "Raman").commit();
        PendingIntent mPedning = PendingIntent.getActivity(MainActivity.this , 0 , mNotificationIntent , 0);
        mBuilder.setContentIntent(mPedning);
        // the below line of code will dismiss the notification when user tap on the anotification.
        mBuilder.setAutoCancel(true);
        mManager.notify(notificationId, mBuilder.build());
    }

    /**
     * create the simple notification
     */
    private void simpleNotificationWithActivity() {
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Simple Notification");
        mBuilder.setContentText("I am Content text , you can change me.");
        int notificationId = 1;
        Intent mNotificationIntent  = new Intent(MainActivity.this , NewActivity.class);
        mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent mPedning = PendingIntent.getActivity(MainActivity.this , 0 , mNotificationIntent , 0);
        mBuilder.setContentIntent(mPedning);
        // the below line of code will dismiss the notification when user tap on the anotification.
        mBuilder.setAutoCancel(true);
        mManager.notify(notificationId, mBuilder.build());
    }

    /**
     * create the simple notification
     */
    private void simpleLargeNotification(Bitmap mBitmap) {
        mBuilder.setLargeIcon(mBitmap);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Bitmap Notification");
        mBuilder.setContentText("Hey, I am the using Bitmap in notification.");
        int notificationId = 2;
        mManager.notify(notificationId, mBuilder.build());
    }

    /**
     * create the simple notification
     */
    private void simpleNotification() {
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Simple Notification");
        mBuilder.setContentText("I am Content text , you can change me.");
        int notificationId = 1;
        mManager.notify(notificationId, mBuilder.build());
    }

    /**
     * create the simple notification
     */
    private void simpleBigTextNotification() {
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Big Text Notification");
        mBuilder.setContentText("I am Large text Notification, Expand me.");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("India is a vast South Asian country with diverse terrain – " +
                        "from Himalayan peaks to Indian Ocean coastline – " +
                        "and history reaching back 5 millennia. In the north, " +
                        "Mughal Empire landmarks include Delhi’s Red Fort complex, " +
                        "massive Jama Masjid mosque and Agra’s iconic Taj Mahal mausoleum. " +
                        "Pilgrims bathe in the Ganges in Varanasi, " +
                        "and Rishikesh is a yoga center and base for Himalayan trekking."));
        int notificationId = 3;
        mManager.notify(notificationId, mBuilder.build());
    }


    class GetBitmap extends AsyncTask<Bitmap , Void , Bitmap>{

        @Override
        protected void onPostExecute(Bitmap mBitmap) {
            super.onPostExecute(mBitmap);
            simpleLargeNotification(mBitmap);
        }
        @Override
        protected Bitmap doInBackground(Bitmap... voids) {
            try {
                Bitmap   bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://www.sendmycake.in/images/app_icon.png").getContent());
                return  bitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }

    private void getLargeImage() {
        new GetBitmap().execute();
    }
}
