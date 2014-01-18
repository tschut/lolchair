package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

@EActivity(R.layout.activity_about)
public class AboutActivity extends Activity {

    @ViewById
    TextView linkWebsite;

    @ViewById
    TextView linkFacebook;

    @ViewById
    TextView linkGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @AfterViews
    void makeLinksClickable() {
        linkWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        linkFacebook.setMovementMethod(LinkMovementMethod.getInstance());
        linkGoogle.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @OptionsItem
    void homeSelected() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
