package com.lolchair.lolchair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.apache.commons.io.IOUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@EActivity(R.layout.activity_submit)
public class SubmitActivity extends Activity {
    public static final String     IMAGE_PATH = "com.lolchair.lolchair.image_path";
    public static final String     IMAGE_URI  = "com.lolchair.lolchair.image_uri";

    @ViewById
    protected ImageView            submitImage;

    @ViewById
    protected TextView             description;

    @ViewById
    protected Button               submitButton;

    @RestService
    protected LolchairServerClient serverClient;

    private Bitmap                 imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @AfterViews
    void setImage() {
        if (getIntent() != null) {
            String imagePath = getIntent().getStringExtra(IMAGE_PATH);
            if (getIntent().getStringExtra(IMAGE_URI) != null) {
                try {
                    Uri imageUri = Uri.parse(getIntent().getStringExtra(IMAGE_URI));
                    imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                } catch (FileNotFoundException e) {
                    // TODO fail better
                    e.printStackTrace();
                }
                submitImage.setImageBitmap(imageBitmap);
            } else {
                imageBitmap = BitmapFactory.decodeFile(imagePath);
                submitImage.setImageBitmap(imageBitmap);
            }
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
        Toast.makeText(this, R.string.submitting_picture, Toast.LENGTH_SHORT).show();
        sendSubmission();
        finish();
    }

    @Background
    void sendSubmission() {
        try {
            File outputDir = getCacheDir();
            File imageFile = File.createTempFile("prefix", "extension", outputDir);
            OutputStream oStream = new FileOutputStream(imageFile);
            imageBitmap.compress(CompressFormat.JPEG, 70, oStream);

            serverClient.submit(description.getText().toString(), IOUtils.toByteArray(new FileInputStream(imageFile)));
            submissionDone();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    protected void submissionDone() {
        Toast.makeText(this, R.string.submission_done, Toast.LENGTH_SHORT).show();
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
