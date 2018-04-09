package com.example.mac.back.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.mac.back.base.ActivityManager;
import com.example.mac.back.utils.NetUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.RequestMethod;
import com.itheima.retrofitutils.listener.UploadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;
import okhttp3.MediaType;
import okhttp3.Response;


/**
 * Created by mac on 2018/3/10.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private Request request;
    public static final String TAG = "CrashHandler";
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //系统默认
    private Thread.UncaughtExceptionHandler defaultHandler;
    private CrashHandler(){}

    private static class InstanceHolder{
        private static final CrashHandler INSTANCE=new CrashHandler();
    }

    public static CrashHandler getInstance(){
        return CrashHandler.InstanceHolder.INSTANCE;
    }



   public void init(Context context){
        mContext = context;
        defaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
   }


//    DexClassLoader
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && defaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, throwable);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.d(TAG, "error:", e);
            }
            //退出程序
          //  android.os.Process.killProcess(android.os.Process.myPid());
           // System.exit(1);
            ActivityManager activitymanager = ActivityManager.getInstance();
            activitymanager.clearAll();


        }
    }


    /**
     * 自定义错误处理，收集错误信息，发送错误报告
     *
     * @param ex true:处理了该异常信息返回true,否则返回false;
     * @return
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        collectDeviceInfo(mContext);
        String filepath=saveCrashInfoToFile(ex);
        com.orhanobut.logger.Logger.e(filepath+"####");
        File file=new File(filepath);
        //TODO 上传log日志文件
        if(NetUtils.isConnected(mContext)){
            request = ItheimaHttp.newUploadRequest(AppConfig.UPLOAD_LOGS, RequestMethod.POST);//apiUrl格式："xxx/xxxxx"
            request.putUploadFile("crash_log",file).putMediaType(MediaType.parse("multipart/form-data"));

            ItheimaHttp.upload(request, new UploadListener() {

                @Override
                public void onResponse(okhttp3.Call call, Response response) {
                    com.orhanobut.logger.Logger.e(response.message()+"@@@@");
                }

                @Override
                public void onProgress(long progress, long total, boolean done) {
                    //上传进度回调progress:上传进度，total:文件长度， done:上传是否完成
                    if(done){
                        com.orhanobut.logger.Logger.e("上传崩溃日志成功");
                    //    ToastUtils.getInstanc(mContext).showToast("上传崩溃日志成功");
                    }
                }

                @Override
                public void onFailure(okhttp3.Call call, Throwable t) {
                    //上传失败
                    com.orhanobut.logger.Logger.e("上传崩溃日志失败"+t.getMessage());
                }
            });

        }

        return true;
    }





    /**
     * 收集设备信息
     *
     * @param mContext
     */
    private void collectDeviceInfo(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.d(TAG, "an error occured when collect crash info", e);
            }
        }
    }
    String path ="";
    /**
     * 保存日志文件
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfoToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timeMillis = System.currentTimeMillis();
            String time = this.format.format(new Date());
            String fileName = "crash" + time + "-" + timeMillis + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //TODO 替换成自己的保存日志文件目录
                path = "/sdcard/crashfile/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return path + fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }



}
