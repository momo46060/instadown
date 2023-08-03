package com.bomb.app.mosalah.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bomb.app.mosalah.MyAdapter;
import com.bomb.app.mosalah.R;
import com.bomb.app.mosalah.Sms;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentMain extends Fragment {
    String string[];
    MyAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        string=getResources().getStringArray(R.array.link);
        recyclerView = view.findViewById(R.id.rv);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        List<Sms> createLists;
        createLists = data();
        Collections.shuffle(createLists);
        adapter = new MyAdapter(getContext(), createLists);
        recyclerView.setAdapter(adapter);




        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AdView mAdView = getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private ArrayList<Sms> data() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < string.length; i++) {
            Sms sms = new Sms();
            sms.setName(string[i]);

            list.add(sms);
        }

        return list;
    }
}
