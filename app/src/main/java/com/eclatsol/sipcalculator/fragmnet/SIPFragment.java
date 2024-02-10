package com.eclatsol.sipcalculator.fragmnet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.model.SIPDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SIPFragment extends Fragment {
    public static final String CLOSE_AMOUNT = "Closing Amount";
    public static final String DATE = "Date";
    public static final String INTEREST = "Interest";
    public static final String INTERNET_CALCULATED_ON = "Interest Calculated On";
    public static final int ITEMS_PER_AD = 6;
    public static final String SR_NO = "Sr No";
    private int compundingValue = 1;
    private ArrayList<HashMap<String, String>> detailsList = new ArrayList<>();
    private Button mBtnCalculate;
    private Button mBtnReset;
    Context mContext;
    private HashMap<String, String> mapInit = new HashMap<>();
    private EditText medtExpectedAnnualRetuns;
    private EditText medtInvestmentAmt;
    private EditText medtInvestmentPeriod;
    public TextView mtxtStartDateofInvestment;
    public Dialog resource_Dialog;
    public String strDate = "";
    private double sumInterest = 0.0d;

    public static Date addDays(Date date, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(2, i);
        String str = "" + instance.get(5) + "/" + (instance.get(2) + 1) + "/" + instance.get(1);
        return instance.getTime();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lay_sip_calculator, viewGroup, false);
        mContext = getActivity();
        detailsList.clear();
        mapInit.clear();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medtInvestmentAmt = (EditText) view.findViewById(R.id.etAmount);
        medtInvestmentPeriod = (EditText) view.findViewById(R.id.etPeroid);
        medtExpectedAnnualRetuns = (EditText) view.findViewById(R.id.etAnnualReturns);
        mtxtStartDateofInvestment = (TextView) view.findViewById(R.id.tvStartDate);
        mtxtStartDateofInvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                Calendar instance = Calendar.getInstance();
                new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        SIPFragment sIPFragment = SIPFragment.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(i3);
                        sb.append("/");
                        int i4 = i2 + 1;
                        sb.append(i4);
                        sb.append("/");
                        sb.append(i);
                        String unused = sIPFragment.strDate = sb.toString();
                        TextView access$100 = mtxtStartDateofInvestment;
                        access$100.setText(i3 + "/" + i4 + "/" + i);
                    }
                }, instance.get(1), instance.get(2), instance.get(5)).show();
            }
        });
        mBtnCalculate = (Button) view.findViewById(R.id.btCalculate);
        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                if (medtInvestmentAmt.getText().toString().equalsIgnoreCase("") || medtInvestmentPeriod.getText().toString().equalsIgnoreCase("") || medtExpectedAnnualRetuns.getText().toString().equalsIgnoreCase("") || mtxtStartDateofInvestment.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String obj = medtInvestmentAmt.getText().toString();
                    String obj2 = medtExpectedAnnualRetuns.getText().toString();
                    String obj3 = medtInvestmentPeriod.getText().toString();
                    String charSequence = mtxtStartDateofInvestment.getText().toString();
                    int parseInt = Integer.parseInt(obj3) * 12;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(charSequence);
                    } catch (Exception e) {
                        e.printStackTrace();
                        date = null;
                    }
                    SIPDataModel calculate = calculate(Double.valueOf(Double.parseDouble(obj)), Double.valueOf(Double.parseDouble(obj2) / 100.0d), compundingValue, parseInt, date);
                    double parseDouble = Double.parseDouble(obj) * Double.parseDouble(obj3) * 12.0d;
                    calculate.setStrAmountInvested(parseDouble + "");
                    calculate.setStrWealthGain((Double.parseDouble(calculate.getFinalClosingAmount()) - parseDouble) + "");
                    try {
                        SIPResultFragment sIPResultFragment = new SIPResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SIPResultFragment.KEY_DETAIL_LIST, calculate);
                        bundle.putSerializable(SIPResultFragment.KEY_EXPECTED_AMOUNT, calculate.getFinalClosingAmount());
                        bundle.putSerializable(SIPResultFragment.KEY_AMOUNT_INVESTED, calculate.getStrAmountInvested());
                        bundle.putSerializable(SIPResultFragment.KEY_WEALTH_GAIN, calculate.getStrWealthGain());
                        sIPResultFragment.setArguments(bundle);
                        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        beginTransaction.addToBackStack("sipResultFragment");
                        beginTransaction.replace(R.id.container_body, sIPResultFragment);
                        beginTransaction.commit();
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                    return;
                }
            }
        });
        mBtnReset = (Button) view.findViewById(R.id.btReset);
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllFeilds();
            }
        });
    }
    

    private SIPDataModel calculate(Double d, Double d2, int i, int i2, Date date) {
        SIPDataModel sIPDataModel;
        int i3;
        int i4 = i2;
        SIPDataModel sIPDataModel2 = new SIPDataModel();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Double valueOf = Double.valueOf(0.0d);
        Double.valueOf(0.0d);
        ArrayList arrayList = new ArrayList();
        HashMap<String, String> hashMap = mapInit;
        String str = SR_NO;
        hashMap.put(str, str);
        HashMap<String, String> hashMap2 = mapInit;
        String str2 = DATE;
        hashMap2.put(str2, str2);
        mapInit.put(INTERNET_CALCULATED_ON, INTERNET_CALCULATED_ON);
        mapInit.put(INTEREST, INTEREST);
        mapInit.put(CLOSE_AMOUNT, CLOSE_AMOUNT);
        detailsList.add(mapInit);
        if (detailsList.size() % 10 == 0) {
            detailsList.add(mapInit);
        }
        sumInterest = 0.0d;
        Double d3 = d;
        int i5 = i;
        Date date2 = date;
        Double d4 = valueOf;
        int i6 = 1;
        while (i6 <= i4) {
            int i7 = i6;
            double d5 = (double) i5;
            Double.isNaN(d5);
            Double valueOf2 = Double.valueOf(((d3.doubleValue() * d2.doubleValue()) * d5) / 12.0d);
            Double d6 = valueOf;
            sumInterest = valueOf2.doubleValue() + sumInterest;
            arrayList.add(valueOf2);
            if (i5 == 1) {
                double doubleValue = d.doubleValue();
                sIPDataModel = sIPDataModel2;
                int i8 = i7;
                double d7 = (double) i8;
                Double.isNaN(d7);
                d4 = Double.valueOf((doubleValue * d7) + sumInterest);
                i3 = i8;
                i5 = i;
            } else {
                sIPDataModel = sIPDataModel2;
                i3 = i7;
                i5--;
            }
            mapInit = new HashMap<>();
            mapInit.put(str, i3 + "");
            mapInit.put(str2, simpleDateFormat.format(date2));
            HashMap<String, String> hashMap3 = mapInit;
            StringBuilder sb = new StringBuilder();
            String str3 = str;
            String str4 = str2;
            sb.append(Math.round(d3.doubleValue()));
            sb.append("");
            hashMap3.put(INTERNET_CALCULATED_ON, sb.toString());
            System.out.println("INTERNET_CALCULATED_ON = " + Math.round(d3.doubleValue()));
            mapInit.put(INTEREST, Math.round(valueOf2.doubleValue()) + "");
            mapInit.put(CLOSE_AMOUNT, Math.round(d4.doubleValue()) + "");
            SIPDataModel sIPDataModel3 = sIPDataModel;
            sIPDataModel3.setFinalClosingAmount(Math.round(d4.doubleValue()) + "");
            detailsList.add(mapInit);
            if (detailsList.size() % 10 == 0) {
                detailsList.add(mapInit);
            }
            if (i3 % i == 0) {
                d3 = Double.valueOf(d.doubleValue() + d4.doubleValue());
                d4 = d6;
            } else {
                d3 = d;
            }
            date2 = addDays(date2, 1);
            i6 = i3 + 1;
            i4 = i2;
            sIPDataModel2 = sIPDataModel3;
            str = str3;
            str2 = str4;
            valueOf = d6;
        }
        SIPDataModel sIPDataModel4 = sIPDataModel2;
        int i9 = i2;
        if (detailsList.size() > i9) {
            Log.v("TAG = ", i9 + "___" + detailsList.size());
            String str5 = (String) detailsList.get(i9).get(CLOSE_AMOUNT);
        }
        String format = simpleDateFormat.format(date2);
        sIPDataModel4.setDetalList(detailsList);
        sIPDataModel4.setMaturityDate(format);
        return sIPDataModel4;
    }

    private double sumOf(ArrayList<Double> arrayList) {
        Iterator<Double> it = arrayList.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            d += it.next().doubleValue();
        }
        return d;
    }

    public void hideKeyBoard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 2);
        } catch (Exception unused) {
        }
    }


    private void clearAllFeilds() {
        medtInvestmentAmt.setText("");
        medtExpectedAnnualRetuns.setText("");
        medtInvestmentPeriod.setText("");
        mtxtStartDateofInvestment.setText("");
    }

    public void onResume() {
        super.onResume();
        mtxtStartDateofInvestment.setText(strDate);
    }



}