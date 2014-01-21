package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.activity_submit)
public class SubmitActivity extends Activity {

    @ViewById
    protected ImageView submitImage;

    @ViewById
    protected TextView  description;

    @ViewById
    protected Button    submitButton;

    @Bean
    protected Mailer    mailer;

    protected Uri       imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @AfterViews
    void setImage() {
        if (getIntent() != null && getIntent().getData() != null) {
            imageUri = getIntent().getData();
            submitImage.setImageURI(imageUri);
        }
    }

    @Click
    void submitButtonClicked() {
        mailer.submit(description.getText().toString(), imageUri);
        finish();
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
