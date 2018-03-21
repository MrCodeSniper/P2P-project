package com.example.mac.back.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import com.example.mac.back.R;
import com.example.mac.back.ui.activity.MainActivity;

import java.util.logging.Logger;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mac on 2018/3/21.
 */

public class NotifyUtils {

    private Context mcontext;
    private  Notification.Builder builder;
    private NotificationChannel channel;
    public  NotificationManager mNotificationManager;


    public NotifyUtils(Context mcontext,String uri) {
        this.mcontext = mcontext;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            mNotificationManager = (NotificationManager) mcontext.getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(mcontext, "1");
        } else {
            //第一步：实例化通知栏构造器Notification.Builder：
            builder = new Notification.Builder(mcontext);//实例化通知栏构造器Notification.Builder，参数必填（Context类型），为创建实例的上下文
        }

        com.orhanobut.logger.Logger.e(uri + "|||");
        // uri="http://blog.csdn.net/itachi85/";
        //第三步：设置通知栏PendingIntent（点击动作事件等都包含在这里）:
       // Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
        //本程序用来响应点击通知的打开应用，第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
        //  PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0, mIntent, 0);
        //重新启动app
        Intent[] appIntent = null;
        appIntent = makeIntentStack(mcontext);//上面有改方法
        appIntent[1].setAction(Intent.ACTION_MAIN);
        appIntent[1].addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent[1].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式
        PendingIntent contentIntent = PendingIntent.getActivities(mcontext, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(mcontext.getResources(), R.mipmap.ic_launcher_round));
        builder.setAutoCancel(true);
        builder.setContentTitle(uri);

        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(mcontext, MainActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(mcontext, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);


    }




    public  void show(){
        mNotificationManager.notify(1,builder.build());//发送通知请求
    }




    Intent[] makeIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, com.example.mac.back.ui.activity.MainActivity.class));
        intents[1] = new Intent(context,  com.example.mac.back.ui.activity.WebViewActivity.class);
        return intents;
    }

}
