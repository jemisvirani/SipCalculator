package com.eclatsol.sipcalculator.fragmnet;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.model.SIPDataModel;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SIPResultFragment extends Fragment implements View.OnClickListener {
    public static final String KEY_AMOUNT_INVESTED = "KEY_AMOUNT_INVESTED";
    public static final String KEY_DETAIL_LIST = "KEY_DETAIL_LIST";
    public static final String KEY_EXPECTED_AMOUNT = "KEY_EXPECTED_AMOUNT";
    public static final String KEY_WEALTH_GAIN = "KEY_WEALTH_GAIN";
    TextView btnDetail;
    Context mContext;
    SIPDataModel sipDataModel;
    String strAmtInvested;
    String strCurrentValue = "000";
    String strExpectedAmt;
    String strWealthGain;
    TextView txt_AmountInvested;
    TextView txt_accpetdamt;
    TextView txt_currentvalue;
    TextView txt_wealthgain;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lay_sip_details, viewGroup, false);
        mContext = getActivity();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_currentvalue = (TextView) view.findViewById(R.id.txt_currentvalue);
        txt_AmountInvested = (TextView) view.findViewById(R.id.txt_AmountInvested);
        txt_accpetdamt = (TextView) view.findViewById(R.id.txt_accpetdamt);
        txt_wealthgain = (TextView) view.findViewById(R.id.txt_wealthgain);
        btnDetail = (TextView) view.findViewById(R.id.btn_details);
        btnDetail.setOnClickListener(this);
        if (getArguments() != null) {
            sipDataModel = (SIPDataModel) getArguments().getSerializable(KEY_DETAIL_LIST);
            strAmtInvested = (String) getArguments().getSerializable(KEY_AMOUNT_INVESTED);
            strExpectedAmt = (String) getArguments().getSerializable(KEY_EXPECTED_AMOUNT);
            strWealthGain = (String) getArguments().getSerializable(KEY_WEALTH_GAIN);
            try {
                double parseDouble = Double.parseDouble(strAmtInvested);
                double parseDouble2 = Double.parseDouble(strWealthGain);
                txt_AmountInvested.setText(Math.round(parseDouble) + "");
                if (!strExpectedAmt.isEmpty()) {
                    txt_accpetdamt.setText(strExpectedAmt);
                }
                txt_wealthgain.setText(Math.round(parseDouble2) + "");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            ArrayList<HashMap<String, String>> detalList = sipDataModel.getDetalList();
            for (int i = 0; i < detalList.size(); i++) {
                HashMap<String, String> hashMap = detalList.get(i);
                String str = hashMap.get(SIPFragment.DATE);
                PrintStream printStream = System.out;
                printStream.println("CheckDate==  modelDate = " + str);
                PrintStream printStream2 = System.out;
                printStream2.println("CheckDate== sdf.format(System.currentTimeMillis()) = " + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
                try {
                    if (simpleDateFormat.parse(str).before(simpleDateFormat.parse(simpleDateFormat.format(Long.valueOf(System.currentTimeMillis() + 86400000))))) {
                        strCurrentValue = hashMap.get(SIPFragment.INTERNET_CALCULATED_ON);
                        PrintStream printStream3 = System.out;
                        printStream3.println("strCurrentValue = " + strCurrentValue);
                        sipDataModel.setCurrentValue(strCurrentValue);
                        txt_currentvalue.setText("₹" + strCurrentValue);
                    }
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_details) {
            if (id != R.id.lay_back_arrow) {
                return;
            }
            onBack();
            return;
        }
        try {
            SIPDetailFragment sIPDetailFragment = new SIPDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(SIPDetailFragment.KEY_DETAIL_ARRAY_LIST, sipDataModel.getDetalList());
            bundle.putString(SIPDetailFragment.KEY_CURRENT_VALUE, sipDataModel.getCurrentValue());
            PrintStream printStream = System.out;
            printStream.println("sipDataModel = " + sipDataModel.getCurrentValue());
            sIPDetailFragment.setArguments(bundle);
            FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            beginTransaction.addToBackStack("SIPDetailFragment");
            beginTransaction.replace(R.id.container_body, sIPDetailFragment);
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void onBack() {
        getActivity().getSupportFragmentManager().popBackStack("sipResultFragment", 1);
    }
}
