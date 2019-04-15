package com.verifone.tony.startapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String packageName = "com.verifone.malaysia.pbb.presentation";
    private Drawable drawable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getApp
        getAppList();


        Button button1 = (Button) findViewById(R.id.button1);
        if (drawable != null) {
            Log.d(TAG, "set button with app's icon");
            button1.setBackground(drawable);
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button1");

                getAppList();

                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if (intent != null) {
                    Log.d(TAG, "Found app");
                    intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void getAppList() {
        PackageManager pm = getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                if (packageInfo.packageName.contains(packageName)) {
                    System.out.println("MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
                    this.drawable = packageInfo.applicationInfo.loadIcon(getPackageManager());
                }

            } else {
                // SystemApp
            }
        }
    }
}
