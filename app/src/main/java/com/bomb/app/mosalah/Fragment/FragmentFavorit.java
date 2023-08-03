package com.bomb.app.mosalah.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bomb.app.mosalah.DBconnection;
import com.bomb.app.mosalah.MyAdapter;
import com.bomb.app.mosalah.R;
import com.bomb.app.mosalah.Sms;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorit extends Fragment {
    ArrayList arrayList;
    DBconnection dBconnection;
    ArrayList<Sms> theimage;
    List<Sms> intros;
     ImageView im;
     private InterstitialAd mInterstitialAd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorit,container,false);
        dBconnection =new DBconnection(getActivity());
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = view.findViewById(R.id.rvf);

       im = view.findViewById(R.id.nofav);
        arrayList=dBconnection.ViewData();
        if (arrayList.isEmpty()){
            im.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            im.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new GridLayoutManager(c,2);
        recyclerView.setLayoutManager(layoutManager);
        intros=prepareData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyAdapter adapter = new MyAdapter(c,intros);
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AdView mAdView = getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        preparAd();
    }
    private ArrayList<Sms> prepareData() {
        arrayList=dBconnection.ViewData();
        theimage = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            Sms createList = new Sms();
            createList.setName(arrayList.get(i).toString());
            theimage.add(createList);
        }
        return theimage;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyAdapter adapter=new MyAdapter();

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        dBconnection =new DBconnection(getActivity());
        final List list=prepareData();
        final RecyclerView recyclerView = getView().findViewById(R.id.rvf);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyAdapter adapter = new MyAdapter(getActivity(),list);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
        arrayList=dBconnection.ViewData();
        if (arrayList.isEmpty()){
            im.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            im.setVisibility(View.GONE);
        }
    }

    private void preparAd() {
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-8270803172827285/7106403988");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

}
