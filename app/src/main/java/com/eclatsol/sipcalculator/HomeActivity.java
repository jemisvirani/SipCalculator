package com.eclatsol.sipcalculator;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eclatsol.sipcalculator.databinding.ActivityHomeBinding;
import com.eclatsol.sipcalculator.fragmnet.SIPFragment;
import com.eggheadgames.siren.ISirenListener;
import com.eggheadgames.siren.Siren;
import com.eggheadgames.siren.SirenAlertType;
import com.eggheadgames.siren.SirenVersionCheckType;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;


public class HomeActivity extends AppCompatActivity {
    private static final String SIREN_JSON_DOCUMENT_URL = "http://eclatsol.net/app_version/sip_calculator_version.json";
    public static Fragment fragment;
    String className;
    private Context mContext;
    ISirenListener sirenListener = new ISirenListener() { 
        @Override 
        public void onCancel() {
        }

        @Override 
        public void onDetectNewVersionWithoutAlert(String str) {
        }

        @Override 
        public void onLaunchGooglePlay() {
        }

        @Override 
        public void onShowUpdateDialog() {
        }

        @Override 
        public void onSkipVersion() {
        }

        @Override 
        public void onError(Exception exc) {
            exc.printStackTrace();
        }
    };
    private String TAG = "HomeActivity";

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = getApplicationContext();
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        className = getClass().getName();
        new Handler();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        mToolbarBack.setVisibility(View.GONE);
//        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//        mFirebaseAnalytics.setSessionTimeoutDuration(500L);
        checkCurrentAppVersion();
        displayView(0);

        float radius = getResources().getDimension(com.intuit.sdp.R.dimen._30sdp);
        NavigationView navigationView = findViewById(R.id.navView);
        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();
        navViewBackground.setShapeAppearanceModel(
                navViewBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
                        .build());

        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        binding.drawerContent.linearPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        binding.drawerContent.linearRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp(HomeActivity.this);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        binding.drawerContent.linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp(HomeActivity.this);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finishAffinity();
                }
            }
        });

    }

    private void displayView(int i) {
        String string = getString(R.string.app_name);
        if (i == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("content_type", "OnClick of SIP Fragment");
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            fragment = new SIPFragment();
        }
        try {
            if (fragment == null) {
                return;
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            getFragmentManager().popBackStack();
            beginTransaction.replace(R.id.container_body, fragment);
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCurrentAppVersion() {
        Siren siren = Siren.getInstance(getApplicationContext());
        siren.setSirenListener(sirenListener);
        siren.setMajorUpdateAlertType(SirenAlertType.FORCE);
        siren.setMinorUpdateAlertType(SirenAlertType.FORCE);
        siren.setPatchUpdateAlertType(SirenAlertType.SKIP);
        siren.setRevisionUpdateAlertType(SirenAlertType.NONE);
        siren.setVersionCodeUpdateAlertType(SirenAlertType.SKIP);
        siren.checkVersion(this, SirenVersionCheckType.IMMEDIATELY, SIREN_JSON_DOCUMENT_URL);
    }


    public static void shareApp(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", R.string.app_name);
            intent.putExtra("android.intent.extra.TEXT", "\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n");
            context.startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception unused) {
            unused.printStackTrace();
        }
    }


    public static void rateApp(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName()));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

}
