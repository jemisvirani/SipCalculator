package com.eclatsol.sipcalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.fragmnet.SIPFragment;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


public class SIPDetailAdapter extends RecyclerView.Adapter<SIPDetailAdapter.ViewHolder> {
    private static  int TYPE_WEEK = 1;
    private static  int TYPE_AVAILABILITY = 0;
    private final ArrayList<HashMap<String, String>> detailsArrayList;
    View itemView;
    private final Context mContext;

    public SIPDetailAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        mContext = context;
        detailsArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sip_detail_row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override 
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        HashMap<String, String> hashMap = detailsArrayList.get(i);
        TextView textView = viewHolder.tvSrNo;
        textView.setText(((Object) hashMap.get(SIPFragment.SR_NO)) + "");
        TextView textView2 = viewHolder.tvDate;
        textView2.setText(((Object) hashMap.get(SIPFragment.DATE)) + "");
        TextView textView3 = viewHolder.tvIntrestCalculatedOn;
        textView3.setText(((Object) hashMap.get(SIPFragment.INTERNET_CALCULATED_ON)) + "");
        TextView textView4 = viewHolder.tvInterest;
        textView4.setText(((Object) hashMap.get(SIPFragment.INTEREST)) + "");
        TextView textView5 = viewHolder.tvClosingAmt;
        textView5.setText(((Object) hashMap.get(SIPFragment.CLOSE_AMOUNT)) + "");



        if (viewHolder.getItemViewType() == TYPE_AVAILABILITY) {
            viewHolder.tvClosingAmt.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            viewHolder.tvDate.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            viewHolder.tvSrNo.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            viewHolder.tvInterest.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            viewHolder.tvIntrestCalculatedOn.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
        }else {
            viewHolder.tvClosingAmt.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            viewHolder.tvDate.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            viewHolder.tvInterest.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            viewHolder.tvSrNo.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            viewHolder.tvIntrestCalculatedOn.setTextColor(ContextCompat.getColor(mContext,R.color.black));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_AVAILABILITY;
        } else {
            return TYPE_WEEK;
        }
    }

    @Override 
    public int getItemCount() {
        PrintStream printStream = System.out;
        printStream.println("detailsArrayList = " + detailsArrayList.size());
        return detailsArrayList.size();
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClosingAmt;
        public TextView tvDate;
        public TextView tvInterest;
        public TextView tvIntrestCalculatedOn;
        public TextView tvSrNo;

        public ViewHolder(View view) {
            super(view);
            tvSrNo = (TextView) view.findViewById(R.id.tvSrNo);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvIntrestCalculatedOn = (TextView) view.findViewById(R.id.tvIntrestCalculatedOn);
            tvInterest = (TextView) view.findViewById(R.id.tvInterest);
            tvClosingAmt = (TextView) view.findViewById(R.id.tvClosingAmt);
        }
    }
}
