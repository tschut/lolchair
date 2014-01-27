package com.lolchair.lolchair;

import java.io.FileNotFoundException;

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
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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

    @AfterViews
    void setListenerOnDescriptionTextView() {
        description.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                submitButtonClicked();
                return true;
            }
        });
    }

    @Click
    void submitButtonClicked() {
        try {
            mailer.submit(description.getText().toString(), getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
        }
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
