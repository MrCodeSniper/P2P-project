package com.example.mac.back.fragment;

/**
 * Created by mac on 2018/2/28.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.mac.back.R;
import com.example.mac.back.activity.WebViewActivity;
import com.example.mac.back.bean.Product;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.utils.IntentUtils;
import com.example.mac.back.viewholder.MyTypeHolder1;
import com.example.mac.back.viewholder.MyTypeHolder2;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.orhanobut.logger.Logger;

import org.itheima.recycler.adapter.BaseRecyclerAdapter;
import org.itheima.recycler.listener.ItemClickSupport;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment_product extends Fragment {


    @BindView(R.id.recycler_view)
    ItheimaRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Request request;

    private Unbinder unbinder;

    private MyTypeAdapter<Product.DataBean> adapter;

    private String baseUrl;

    private List<Product.DataBean> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {

        ItemClickSupport itemClickSupport = new ItemClickSupport(recyclerView);
//点击事件
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                if(position==0){
                    String[] key={"url","title"};
                    String[] values={AppConfig.PARDEN,"新手乐园"};
                    IntentUtils.showIntent(getActivity(), WebViewActivity.class,key,values);
                }

                Toast.makeText(recyclerView.getContext(), "我被点击了"+position, Toast.LENGTH_SHORT).show();
            }
        });
//长按事件
//        itemClickSupport.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
//                Toast.makeText(recyclerView.getContext(), "我被长按了", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

    }

    private void initAdapterAndListener() {
        //adapter = new BaseRecyclerAdapter(recyclerView, MyRecyclerViewHolder.class, R.layout.item_reyclerview, datas);
        adapter = new MyTypeAdapter<Product.DataBean>(recyclerView,list);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Call call = ItheimaHttp.send(request, new HttpResponseListener<Product>() {
                    @Override
                    public void onResponse(Product product, Headers headers) {
                       // Logger.d("服务端数据量:"+product.getData().size());
                        List<Product.DataBean> mlist=new ArrayList<>();
                        Product.DataBean dataBean1=new Product.DataBean();
                        mlist.add(dataBean1);
                        for(int i=0;i<product.getData().size();i++){
                            Product.DataBean dataBean=product.getData().get(i);
                            mlist.add(dataBean);
                        }
                        adapter.addDatas(false,mlist);
                        Toast.makeText(getActivity(), "下拉刷新了所有数据", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    /**
                     * 可以不重写失败回调
                     * @param call
                     * @param e
                     */
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable e) {
                        Logger.e("error:"+e.getMessage());
                    }
                });

            }
        });


    }

    private void initData() {
        Product.DataBean dataBean1=new Product.DataBean();
        list.add(dataBean1);

        request = ItheimaHttp.newGetRequest(AppConfig.PRODUCT_LIST);//apiUrl格式："xxx/xxxxx"
        Call call = ItheimaHttp.send(request, new HttpResponseListener<Product>() {
            @Override
            public void onResponse(Product product, Headers headers) {
                Logger.d("服务端数据量:"+product.getData().size());
                for(int i=0;i<product.getData().size();i++){
                    Product.DataBean dataBean=product.getData().get(i);
                    list.add(dataBean);
                }
               // Logger.d("list:"+list+"size:"+list.size());
                initAdapterAndListener();
            }

            /**
             * 可以不重写失败回调
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Logger.e("error:"+e.getMessage());
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        list.clear();
       // adapter.clearAllData();
    }



    class MyTypeAdapter<T> extends BaseRecyclerAdapter {
        private final int ITEM_TYPE_1 = 0;
        private final int ITEM_TYPE_2 = 1;



        public MyTypeAdapter(RecyclerView recyclerView, List<T> datas) {
            super(recyclerView, null, 0, datas);
            mDatas=datas;
        }

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //选择类型
            if (viewType == ITEM_TYPE_1) {
                MyTypeHolder1 myTypeHolder1=new MyTypeHolder1(parent, R.layout.product_one);
                return myTypeHolder1;
            }
            return new MyTypeHolder2(parent, R.layout.product_two);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            if(holder.getItemViewType()==1&&position!=0){

//                Logger.e("xxx:"+holders.getData().getCategory());
                Product.DataBean now= (Product.DataBean) mDatas.get(position);
                Product.DataBean before= (Product.DataBean) mDatas.get(position-1);
                MyTypeHolder2 holders=(MyTypeHolder2)holder;
                if(TextUtils.equals(now.getCategory(),before.getCategory())){
                    holders.dismissTitle();
                }else {
                    holders.undismissTitle();
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            //根据position返回类型
            if(position == 0){
                return ITEM_TYPE_1;
            }else{
                return ITEM_TYPE_2;
            }
        }


        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            holder.onBindData(position, this.mDatas.get(position));
        }
    }








}