package com.bomb.app.mosalah;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bomb.app.mosalah.databinding.ActivityViewimgBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class viewimg extends AppCompatActivity implements View.OnClickListener {
     File file;
     Bitmap bitmap;
     String getName;
     Bitmap bitmap5;
     ArrayList arrayList;
     InterstitialAd mInterstitialAd;
      DBconnection dBconnection;
     ActivityViewimgBinding binding;
    int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewimg);
        dBconnection=new DBconnection(viewimg.this);
        getSupportActionBar().hide();
        preparAd();
        getName = getIntent().getExtras().getString("img");
        Picasso.get().load(getName).placeholder(R.drawable.loo).into(binding.img);
        binding.set.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.sahar.setOnClickListener(this);
        binding.fabfav.setOnClickListener(this);
        arrayList = dBconnection.ViewData();
        if (arrayList.contains(getName)) {
            binding.fabfav.setImageResource(R.drawable.likw);
        } else if (!arrayList.contains(getName)) {
            binding.fabfav.setImageResource(R.drawable.dilike);
        }

    }


    private void preparAd() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("990670AC8116BDAD15834C5C681424E9")
                .build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8270803172827285/4578977831");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("990670AC8116BDAD15834C5C681424E9")
                .build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice("990670AC8116BDAD15834C5C681424E9")
                        .build());
            }

        });

    }

    public void showad() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void set() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = Picasso.get()
                            .load(getName)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            file = new File(this.getExternalCacheDir(), "logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Intent intent1 = new Intent();
            intent1.setAction(Intent.ACTION_ATTACH_DATA);
            File file = new File(this.file.getPath());
            intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//            intent.setDataAndType(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file), getMimeType(this.file.getPath()));
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(this.file.getPath()));
//            intent.setDataAndType(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file), getMimeType(this.file.getPath()));
            intent1.setData(uri);
            startActivity(Intent.createChooser(intent1, "Set"));


        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Exception generated", Toast.LENGTH_SHORT).show();
        }
    }

    public void sshare() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = Picasso.get()
                            .load(getName)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            file = new File(this.getExternalCacheDir(), "logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent share = new Intent("android.intent.action.SEND");
        share.setType("image/*");
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(this.file.getPath()));
        share.putExtra("android.intent.extra.STREAM", uri);

        startActivity(Intent.createChooser(share, "via"));
    }

    public void save() throws IOException {
        Toast.makeText(this, "انتظر حتى يتم نحميل البوست", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bitmap5 = Picasso.get()
                                .load(getName)
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            FileOutputStream outStream = null;
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/Liverpool Wallpaper/");
            dir.mkdirs();
            String fileName = String.format(System.currentTimeMillis() + ".png", System.currentTimeMillis());
            File outFile = new File(dir, fileName);
            outStream = new FileOutputStream(outFile);
            bitmap5.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        }
        Toast.makeText(this, " تم الحفظ", Toast.LENGTH_SHORT).show();
    }


    public void fav() {
        arrayList = dBconnection.ViewData();
        if (arrayList.contains(getName)) {
            int count = dBconnection.delet(getName);
            binding.fabfav.setImageResource(R.drawable.dilike);
            Toast.makeText(viewimg.this, R.string.fav_delet, Toast.LENGTH_LONG).show();
        } else if (!arrayList.contains(getName)) {

            long id = dBconnection.inserdata(getName);
            binding.fabfav.setImageResource(R.drawable.likw);
             n = 1;
            if (id < 0) {
                Toast.makeText(viewimg.this, "حدث خطا ما الرجاء التواصل مع المطور", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(viewimg.this, R.string.fav_add, Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 0;
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
                //File write logic here
                return true;
            }
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(String.valueOf(this), "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set:

                set();
                showad();
                break;
            case R.id.save:
                try {
                    save();
                    showad();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.fabfav:
                showad();
                fav();
                break;
            case R.id.sahar:
                sshare();
                showad();
                break;
            default:
                Toast.makeText(this, "hgchgcgc", Toast.LENGTH_SHORT).show();
        }
    }
}
