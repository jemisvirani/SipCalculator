package com.eclatsol.sipcalculator.fragmnet;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.eclatsol.sipcalculator.HomeActivity;
import com.eclatsol.sipcalculator.R;


public class AboutUsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AboutUsFragment";
    Context mContext;
    TextView mTxtVersionName;
    TextView mtvAboutus2;
    TextView mtvaboutus1;
    TextView mtvaboutus3;
    View rootView;
    TextView textContact;
    TextView textMail;
    TextView txt_appVersion;
    TextView txt_support;
    TextView txt_title;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.lay_aboutus, viewGroup, false);
        this.mContext = getActivity();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        HomeActivity.mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        HomeActivity.mToolbarBack.setVisibility(View.VISIBLE);
//        HomeActivity.mToolbarBack.setOnClickListener(this);
//        HomeActivity.mToolbarOverflow.setVisibility(View.GONE);
//        HomeActivity.mToolbarTiltel.setTextColor(getResources().getColor(R.color.white));
//        HomeActivity.mToolbarTiltel.setText("About Us");
        init();
        getVersionInfo();
        return rootView;
    }

    private void init() {
        Typeface createFromAsset = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PT_Serif-Web-Regular.ttf");
        Typeface createFromAsset2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PT_Serif-Web-Bold.ttf");
        mTxtVersionName = (TextView) rootView.findViewById(R.id.txt_versionName);
        mTxtVersionName.setTypeface(createFromAsset);
        mtvaboutus1 = (TextView) rootView.findViewById(R.id.txt_aboutus1);
        mtvaboutus1.setTypeface(createFromAsset);
        mtvAboutus2 = (TextView) rootView.findViewById(R.id.txt_aboutus2);
        mtvAboutus2.setTypeface(createFromAsset);
        mtvaboutus3 = (TextView) rootView.findViewById(R.id.txt_aboutus3);
        mtvaboutus3.setTypeface(createFromAsset);
        textMail = (TextView) rootView.findViewById(R.id.txt_mail);
        textMail.setTypeface(createFromAsset);
        textContact = (TextView) rootView.findViewById(R.id.txt_contact);
        textContact.setTypeface(createFromAsset);
        txt_title = (TextView) rootView.findViewById(R.id.text_title);
        txt_title.setTypeface(createFromAsset2);
        txt_appVersion = (TextView) rootView.findViewById(R.id.txt_appversion);
        txt_appVersion.setTypeface(createFromAsset2);
        txt_support = (TextView) rootView.findViewById(R.id.txt_support);
        txt_support.setTypeface(createFromAsset2);
    }

    private void getVersionInfo() {
        String str = "";
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            str = packageInfo.versionName;
            int i = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.mTxtVersionName.setText(str);
    }

    @Override 
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        HomeActivity.mToolbarTiltel.setText("About Us");
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() { 
            @Override 
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && i == 4) {
                    getActivity().getSupportFragmentManager().popBackStack("aboutus", 1);
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
        getActivity().getSupportFragmentManager().popBackStack("aboutus", 1);
    }
}
