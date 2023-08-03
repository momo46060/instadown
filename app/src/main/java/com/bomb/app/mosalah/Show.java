package com.bomb.app.mosalah;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bomb.app.mosalah.Fragment.TitleAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;

public class Show extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        preparAd();
        FragmentManager fragmentManager=getSupportFragmentManager();

        TitleAdapter titleAdapter=new TitleAdapter(fragmentManager,this);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(titleAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
    @Override
    public void onBackPressed() {
                  new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.ouut))

                    .setMessage(getString(R.string.maseg))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showad();
                            finish();

                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(getString(R.string.rate), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showad();
                            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.bomb.app.ahlawy"));
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.bomb.app.mosalah"));
                            startActivity(i);
                        }
                    })
                    .setIcon(R.drawable.r)
                    .show();

        }

    public void showad() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    private void preparAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8270803172827285/4578977831");
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


