package com.eclatsol.sipcalculator.fragmnet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.eclatsol.sipcalculator.HomeActivity;
import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.adapter.CustomBaseAdapter;
import com.eclatsol.sipcalculator.model.OurOtherAppRowItemModel;
import java.util.ArrayList;
import java.util.List;


public class OurOtherApp extends Fragment implements View.OnClickListener {
    private String TAG = "OurOtherApp";
    ListView listView;
    Context mContext;
    View rootView;
    List<OurOtherAppRowItemModel> rowItems;
    public static final String[] titles = {"Expense Tracker - Money Manager", "EMI Calculator", "Deposit Calculator FD & RD", "Income Tax Calculator", "All India PIN Code Directory", "Local Reporter India", "Monthly Saving Calculator"};
    public static final Integer[] images = {Integer.valueOf((int) R.drawable.expense_tracker), Integer.valueOf((int) R.drawable.app_icon_emi), Integer.valueOf((int) R.drawable.app_icon_deposit), Integer.valueOf((int) R.drawable.app_icon_tax_calc), Integer.valueOf((int) R.drawable.app_icon_indian_pin_codes), Integer.valueOf((int) R.drawable.reporter_india), Integer.valueOf((int) R.drawable.monthly_saving)};

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.lay_other_apps, viewGroup, false);
        this.mContext = getActivity();
//        HomeActivity.mToolbarBack.setVisibility(View.VISIBLE);
//        HomeActivity.mToolbarBack.setOnClickListener(this);
//        HomeActivity.mToolbarOverflow.setVisibility(View.GONE);
//        HomeActivity.mToolbarTiltel.setTextColor(getResources().getColor(R.color.white));
        this.rowItems = new ArrayList();
        for (int i = 0; i < titles.length; i++) {
            this.rowItems.add(new OurOtherAppRowItemModel(images[i].intValue(), titles[i]));
        }
        this.listView = (ListView) this.rootView.findViewById(R.id.list);
        this.listView.setAdapter((ListAdapter) new CustomBaseAdapter(getActivity(), this.rowItems) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                if (i2 == 0) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.expensetracker")));
                } else if (i2 == 1) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.toolsandcalculators")));
                } else if (i2 == 2) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.DepositCalculators")));
                } else if (i2 == 3) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.incometaxcalculator")));
                } else if (i2 == 4) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.pinfinder")));
                } else if (i2 == 5) {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.localreporterindia")));
                } else if (i2 != 6) {
                } else {
                    OurOtherApp.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.eclatsol.monthlysaving")));
                }
            }
        });

        return this.rootView;
    }

    public void onBack() {
        getActivity().getSupportFragmentManager().popBackStack("OurOtherApp", 1);
    }

    @Override 
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override 
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && i == 4) {
                    OurOtherApp.this.onBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override 
    public void onClick(View view) {
//        if (view.getId() != R.id.lay_back_arrow) {
//            return;
//        }
        onBack();
    }
}
