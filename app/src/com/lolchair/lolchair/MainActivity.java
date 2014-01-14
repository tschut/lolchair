package com.lolchair.lolchair;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.view.Menu;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
