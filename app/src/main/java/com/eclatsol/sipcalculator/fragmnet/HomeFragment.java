package com.eclatsol.sipcalculator.fragmnet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.eclatsol.sipcalculator.HomeActivity;
import com.eclatsol.sipcalculator.R;


public class HomeFragment extends Fragment {
    public static Fragment fragment;
    Context mContext;
    private Dialog resource_Dialog;

    LinearLayout linearLayout;
    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.home_fragment, viewGroup, false);
        mContext = getActivity();
//        ((AppCompatActivity) getActivity()).setSupportActionBar(HomeActivity.mToolbar);
//        HomeActivity.mToolbarBack.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.home_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    beginTransaction.addToBackStack("SIPFragment");
                    beginTransaction.replace(R.id.container_body, new SIPFragment());
                    beginTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
