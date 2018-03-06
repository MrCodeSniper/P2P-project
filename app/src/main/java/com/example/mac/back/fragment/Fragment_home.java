package com.example.mac.back.fragment;

/**
 * Created by mac on 2018/2/28.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mac.back.R;
import com.example.mac.back.activity.LoginActivity;
import com.example.mac.back.activity.WebViewActivity;
import com.example.mac.back.adapter.RecyclingPagerAdapter;
import com.example.mac.back.utils.IntentUtils;
import com.example.mac.back.view.AutoVerticalScrollTextView;
import com.example.mac.back.view.ClipViewPager;
import com.example.mac.back.view.RoundImageView;
import com.example.mac.back.view.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment_home extends Fragment {
    @BindView(R.id.page_container)
    RelativeLayout pageContainer;
    private TubatuAdapter mPagerAdapter;

    AutoVerticalScrollTextView verticalScrollTV;
    Unbinder unbinder;
    @BindView(R.id.vp_homepage)
    ClipViewPager mViewPager;

    private int number = 0;
    private boolean isRunning = true;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                verticalScrollTV.next();
                number++;
                verticalScrollTV.setText(strings[number % strings.length]);
            }
        }
    };
    private String[] strings = {"招财猫理财 经营许可证 :浙ICP证B2-20150235", "【加奖】邀请好友限时加奖，最高获得100元",};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        verticalScrollTV = view.findViewById(R.id.textview_auto_roll);
        LinearLayout ll_user_parden=view.findViewById(R.id.ll_user_parden);
        ll_user_parden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.showIntent(getActivity(), WebViewActivity.class);
            }
        });
        Button btn_register=view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.showIntent(getActivity(), LoginActivity.class);
            }
        });
        unbinder = ButterKnife.bind(this, view);
        verticalScrollTV.setText(strings[0]);
        postTextChange();
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        pageContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mPagerAdapter = new TubatuAdapter(getActivity());
        mViewPager.setAdapter(mPagerAdapter);
        initData();
        return view;
    }

    private void initData() {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.home_theme_one);
        list.add(R.drawable.home_theme_two);
        list.add(R.drawable.home_theme_three);
        list.add(R.drawable.home_theme_four);

        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(Math.min(list.size(), 4));
        mPagerAdapter.addAll(list);

    }


    public static class TubatuAdapter extends RecyclingPagerAdapter {
        private LayoutInflater inflater;
        private final List<Integer> mList;
        private final Context mContext;

        public TubatuAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
            this.inflater=LayoutInflater.from(context);
        }

        public void addAll(List<Integer> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            RoundImageView imageView = null;
            if (convertView == null) {
//                imageView = new ImageView(mContext);overScrollMode
                imageView= (RoundImageView) inflater.inflate(R.layout.homepage_theme_item,null);
            } else {
                imageView = (RoundImageView) convertView;
            }
            imageView.setTag(position);
            imageView.setImageResource(mList.get(position));
            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }


    private void postTextChange() {
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRunning = false;
        unbinder.unbind();
    }
}