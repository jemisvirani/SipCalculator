package com.eclatsol.sipcalculator.fragmnet;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eclatsol.sipcalculator.HomeActivity;
import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.adapter.SIPDetailAdapter;

import java.util.ArrayList;


public class SIPDetailFragment extends Fragment {
    public static final String KEY_CURRENT_VALUE = "KEY_CURRENT_VALUE";
    public static final String KEY_DETAIL_ARRAY_LIST = "KEY_DETAIL_ARRAY_LIST";
    private Context mContext;
    private TextView mTxtCurrentValue;
    private RecyclerView recyclSipDetail;
    private View rootview;
    private String strCurrentValue = "000";

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mContext = getActivity();
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_detail_sip, viewGroup, false);
    }

    @Override 
    public void onViewCreated(View view, Bundle bundle) {
        init(view);
        if (getArguments() != null) {
            if (getArguments().getSerializable(KEY_CURRENT_VALUE) != null) {
                strCurrentValue = (String) getArguments().getSerializable(KEY_CURRENT_VALUE);
                TextView textView = mTxtCurrentValue;
                textView.setText("₹" + strCurrentValue);
            } else {
                TextView textView2 = mTxtCurrentValue;
                textView2.setText("₹" + strCurrentValue);
            }
            recyclSipDetail.setAdapter(new SIPDetailAdapter(mContext, (ArrayList) getArguments().getSerializable(KEY_DETAIL_ARRAY_LIST)));
        }
    }

    private void init(View view) {
        recyclSipDetail = (RecyclerView) view.findViewById(R.id.recycleRecurringDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclSipDetail.setLayoutManager(linearLayoutManager);
        mTxtCurrentValue = (TextView) view.findViewById(R.id.txt_maturityDate);
    }


    public void onBack() {
        getActivity().getSupportFragmentManager().popBackStack("SIPDetailFragment", 1);
    }

    @Override 
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override 
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && i == 4) {
                    onBack();
                    return true;
                }
                return false;
            }
        });
    }
}
