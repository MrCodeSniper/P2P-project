package com.example.mac.back.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mac.back.R;
import com.example.mac.back.bean.UserTicket;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by mac on 2018/4/19.
 */

public class UserTicketAdapter extends RecyclerView.Adapter<UserTicketAdapter.ViewHolder> {


    private UserTicket userTicket;

    public UserTicketAdapter(UserTicket userTicket) {
        this.userTicket = userTicket;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         UserTicket.DataBean dataBean = userTicket.getData().get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date end=sdf.parse(dataBean.getEnd_date());
            Date now=new Date();
            if(end.compareTo(now)==1||end.compareTo(now)==0){
                holder.ll_background.setBackgroundResource(R.drawable.bg_user_rate_coupon_able);
            }else {
                holder.ll_background.setBackgroundResource(R.drawable.bg_user_rate_coupon_past);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dataBean.getType()==0){
            holder.tv_ticketfunc.setTextSize(27);
        }else {
            holder.tv_ticketfunc.setTextSize(20);
        }




        holder.tv_ticketfunc.setText(dataBean.getType()==0?dataBean.getInterest_rate()*100+"%":dataBean.getOffset_account()*10000+"元");
        holder.tv_ticketname.setText(dataBean.getType()==0?"新手礼包加息券":"新手礼包抵用券");
        holder.tv_tickettime.setText(dataBean.getBegin_date().substring(0,10)+"-"+dataBean.getEnd_date().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return userTicket.getData().size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll_background;
        TextView tv_ticketfunc;
        TextView tv_ticketname;
        TextView tv_tickettime;

        public ViewHolder(View view) {
            super(view);
            ll_background = (LinearLayout) view.findViewById(R.id.ll_background);
            tv_ticketfunc = (TextView) view.findViewById(R.id.tv_ticketfunc);
            tv_ticketname =(TextView) view.findViewById(R.id.tv_ticketname);
            tv_tickettime=(TextView) view.findViewById(R.id.tv_tickettime);
        }
    }








}
