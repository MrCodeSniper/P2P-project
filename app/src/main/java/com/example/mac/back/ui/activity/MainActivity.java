package com.example.mac.back.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.application.MyApplication;
import com.example.mac.back.bean.Product;
import com.example.mac.back.bean.UserBank;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.data.ClientThread;
import com.example.mac.back.data.ProductApi;

import com.example.mac.back.service.PushService;
import com.example.mac.back.ui.adapter.MyFragmentAdapter;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.ui.contract.UpdateContract;
import com.example.mac.back.ui.contract.UserDetailContract;
import com.example.mac.back.ui.fragment.Fragment_home;
import com.example.mac.back.ui.presenter.UpdatePresenter;
import com.example.mac.back.ui.presenter.UserBankPresenter;
import com.example.mac.back.ui.presenter.UserPresenter;
import com.example.mac.back.utils.ApkUtils;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.example.mac.back.utils.StatusBarUtils;
import com.example.mac.back.view.ScrollOrNotViewPager;
import com.huxq17.handygridview.HandyGridView;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ProgressParams;
import com.orhanobut.logger.Logger;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener ,UpdateContract.View,PushService.UpdateUI{


    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String mNewApk = PATH + "/new.apk";
    private ImageView iv_center_three;
    private ImageView iv_center_two;
    private ImageView iv_center;
    private ImageView main_image_center;
    private CircleDialog.Builder builder;
    private boolean isClickChangeTab=false;
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = { Fragment_home.class, Fragment_home.class,Fragment_home.class,Fragment_home.class,Fragment_home.class };
    private int imageViewArray[] = { R.drawable.tab_home, R.drawable.tab_produce,R.drawable.circle_pre,R.drawable.tab_my,R.drawable.tab_more };
    private String textViewArray[] = { "首页", "分类","sss","xxx","xxx"};
    private List<Fragment> list = new ArrayList<Fragment>();
    private List<String> lists=new ArrayList<>();
    private ScrollOrNotViewPager vp;



     UpdatePresenter mPresenter;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;


    DialogFragment dialogFragment;


    private boolean isAnimProcess=false;
    private boolean isshow=false;
    private AnimatorSet animSet;
    private AnimatorSet.Builder anim_builder;


    private ProductApi api;


    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        initView();//初始化控件
        StatusBarUtils statusBarUtils=new StatusBarUtils(this);
        statusBarUtils.initStatuBar(R.color.maincolor);
    }

    @Override
    protected void initData() {
        initPage();
        //检查更新客户端
        api=MyApplication.getsInstance().getAppComponent().getProductApi();
        mPresenter=new UpdatePresenter(api,this);
        mPresenter.attachView(this);
        mPresenter.getNewestAPKVersion();
    }

    @Override
    protected void initListener() {
//        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });


        main_image_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn) {
                if(!isAnimProcess&&!isshow){//不在动画执行中且未显示
                    isshow=true;
                    setAnimate1(iv_center,false);
                    setAnimate2(iv_center_two,false);
                    setAnimate3(iv_center_three,false);
                    rotateAnimate(main_image_center,false);
                }else if(!isAnimProcess&isshow){
                    isshow=false;
                    setAnimate1(iv_center,true);
                    setAnimate2(iv_center_two,true);
                    setAnimate3(iv_center_three,true);
                    rotateAnimate(main_image_center,true);
                }
            }
        });
    }

    /*初始化Fragment*/
    private void initPage() {
        lists.add("home");
        lists.add("product");
        lists.add("my");
        lists.add("more");

        //绑定Fragment适配器
        vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), lists));
        mTabHost.getTabWidget().setDividerDrawable(null);//分割线样式
    }



    //    控件初始化控件
    private void initView() {
        iv_center=findViewById(R.id.center);
        iv_center_two=findViewById(R.id.center_two);
        iv_center_three=findViewById(R.id.center_three);
        vp = (ScrollOrNotViewPager) findViewById(R.id.pager);
        vp.setScroll(true);
        main_image_center=findViewById(R.id.main_image_center);
        /*实现OnPageChangeListener接口,目的是监听Tab选项卡的变化，然后通知ViewPager适配器切换界面*/
        /*简单来说,是为了让ViewPager滑动的时候能够带着底部菜单联动*/

        vp.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器

        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);//绑定tahost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        mTabHost.setOnTabChangedListener(this);


        int count = textViewArray.length;

        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        if(position>=2){
            mTabHost.setCurrentTab(position+1);//根据位置Postion设置当前的Tab
        }else{
            mTabHost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        }

        widget.setDescendantFocusability(oldFocusability);//设置取消分割线
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {

        int position = mTabHost.getCurrentTab();
            if(position>=2){
                vp.setCurrentItem(position-1);
                isClickChangeTab=true;
            }else{
                vp.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
            }

    }





    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setImageResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }

    @Override
    public void showUpdateDialog(String message) {

        com.orhanobut.logger.Logger.e("显示dialog"+"未增加内容");

//        CustomDialogBuilder.getInstance(MainActivity.this)
//                .setMessage("测试")
//                .setTitle("版本更新")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //下载差分包
//                        dialog.cancel();
//                        mPresenter.getNewestPatch();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
//                .getFinalDialog().show();


        builder=new CircleDialog.Builder(this);
        builder.setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setTitle("测试")
                .setText(message)
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //下载差分包
                        mPresenter.getNewestPatch();
                    }
                })
                .setNegative("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }



    @Override
    public void showDownloadDialog() {
        builder = new CircleDialog.Builder(this);
        builder.setCancelable(false).setCanceledOnTouchOutside(false)
                .setTitle("下载")
                .setProgressText("已经下载")
//                        .setProgressText("已经下载%s了")
//                        .setProgressDrawable(R.drawable.bg_progress_h)
//                .setNegative("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
                .show();
    }

    @Override
    public void prepareMerge() {

    }

    @Override
    public void retrunToInstall() {

    }

    @Override
    public void changeProgress(int progress) {
//        com.orhanobut.logger.Logger.e(progress+"xdss");
        builder.setProgress(100, progress).create();
    }

    @Override
    public void dowloadFinish() {
        builder.setProgressText("下载完成").create().dismiss();
//        mPresenter.old_to_newAPK();
        merge();
    }



    private void merge() {
        if (RePlugin.isPluginInstalled("app-debug")) {
            RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("app-debug", "com.wang.appaddupdatedemo.FirstActivity"));
        } else {
            Toast.makeText(MainActivity.this, "You must install plugin first!", Toast.LENGTH_SHORT).show();
            final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Installing...", "Please wait...", true, true);
            // FIXME: 仅用于安装流程演示 2017/7/24
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    simulateInstallExternalPlugin();
                    pd.dismiss();
                }
            }, 1000);
        }
    }


        /**
         * 模拟安装或升级（覆盖安装）外置插件
         * 注意：为方便演示，外置插件临时放置到Host的assets/external目录下，具体说明见README</p>
         */
    private void simulateInstallExternalPlugin() {
        String demo3Apk= "app-debug.apk";
        String demo3apkPath =demo3Apk;

        // 文件是否已经存在？直接删除重来
        String pluginFilePath = getFilesDir().getAbsolutePath() + File.separator + demo3Apk;
        File pluginFile = new File(pluginFilePath);
        if (pluginFile.exists()) {
            FileUtils.deleteQuietly(pluginFile);
        }

        // 开始复制
        copyAssetsFileToAppFiles(demo3apkPath, demo3Apk);
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(pluginFilePath);
        }

        if (info != null) {
            RePlugin.startActivity(MainActivity.this, RePlugin.createIntent(info.getName(), "com.wang.appaddupdatedemo.FirstActivity"));
        } else {
            Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            ApkUtils.installApk(this,mNewApk);
        }
    }

    /**
     * 从assets目录中复制某文件内容
     *  @param  assetFileName assets目录下的Apk源文件路径
     *  @param  newFileName 复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(String assetFileName, String newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = this.getAssets().open(assetFileName);
            fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void dowloadError() {
        builder.setProgressText("下载失败").create().dismiss();
    }

    @Override
    public void showMergeDialog() {
        dialogFragment = new CircleDialog.Builder(this)
                .setProgressText("合并中...")
                .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
                .show();


    }

    @Override
    public void mergeSuccess() {
        com.orhanobut.logger.Logger.e("合并成功");
        //安装
        dialogFragment.dismiss();
      //  ApkUtils.installApk(MainActivity.this, AppConfig.NEWAPKPATH);
    }

    @Override
    public void mergeFailed() {
        com.orhanobut.logger.Logger.e("合并失败");
        dialogFragment.dismiss();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }


    /**
     * 初始化推送
     */
    @Override
    protected void initSocketService() {
        super.initSocketService();
//        Intent intent = new Intent(this,PushService.class);
//        startService(intent);
    }


    @Override
    public void updateUI() {

    }

    //////////////////////////////////////////////////////////////////////////////



    private void  setAnim(ImageView iv,List<ObjectAnimator> list,boolean isReverse,int duration){
        animSet =new AnimatorSet();
        for(int i=0;i<list.size();i++){
            if(i==0){
                anim_builder=animSet.play(list.get(0));
            }else {
                anim_builder.with(list.get(i));
            }
        }
        animSet.setDuration(duration);
        if(isReverse){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                animSet.reverse();
            }
        }else {
            animSet.start();
        }
    }

    private void setAnimate2(ImageView iv,boolean isReverse){
        iv.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationY", 0, -220, -200,-200);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "translationX", 0, -220, -200,-200);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(iv,"alpha",0,0.5f,1f);
        List<ObjectAnimator> list=new ArrayList<>();
        list.add(alphaAnim);
        list.add(objectAnimator);
        list.add(objectAnimator2);
        setAnim(iv,list,isReverse,1000);
    }

    private void setAnimate3(ImageView iv,boolean isReverse){
        iv.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(iv,"alpha",0,0.5f,1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationY", 0, -220, -200,-200);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "translationX", 0, 220, 200,200);
        List<ObjectAnimator> list=new ArrayList<>();
        list.add(alphaAnim);
        list.add(objectAnimator);
        list.add(objectAnimator2);
        setAnim(iv,list,isReverse,1000);
    }


    /**
     * 属性动画
     * 平移
     */
    private void setAnimate1(ImageView iv,boolean isReverse){
        iv.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(iv,"alpha",0,0.5f,1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationY", 0, -220, -200,-200);
        List<ObjectAnimator> list=new ArrayList<>();
        list.add(alphaAnim);
        list.add(objectAnimator);
        setAnim(iv,list,isReverse,1000);
        animSet.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation){
                isAnimProcess=true;
            }

            @Override
            public void onAnimationRepeat(Animator animation){

            }

            @Override
            public void onAnimationEnd(Animator animation){
                isAnimProcess=false;

            }

            @Override
            public void onAnimationCancel(Animator animation){

            }
        });

    }


    private void rotateAnimate(ImageView iv,boolean isReverse){
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"rotation",0,0,720);
        List<ObjectAnimator> list=new ArrayList<>();
        list.add(animator);
        setAnim(iv,list,isReverse,1000);
    }

    ////////////////////////////////////////////////////////////////

    public void setCanDrag(boolean canDrag){
        vp.setScroll(canDrag);
    }
    private OnChangeModeListener listener;


    public interface OnChangeModeListener{
        void changeMode(MotionEvent ev);
    }

    public void setOnChangeModeListener(OnChangeModeListener Listener){
        this.listener = Listener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != listener) {

            View view=getCurrentFocus();//
            if(view!=null&&! (view instanceof HandyGridView)){
                listener.changeMode(ev);
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}
