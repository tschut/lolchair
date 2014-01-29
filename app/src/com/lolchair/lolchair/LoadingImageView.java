package com.lolchair.lolchair;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

@EViewGroup(R.layout.loading_image_view)
public class LoadingImageView extends RelativeLayout implements Target {
    @ViewById
    ProgressBar spinner;

    @ViewById
    ImageView   loadedImage;

    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBitmapFailed(Drawable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
        spinner.setVisibility(View.GONE);
        loadedImage.setVisibility(View.VISIBLE);
        loadedImage.setImageBitmap(bitmap);
    }

    @Override
    public void onPrepareLoad(Drawable arg0) {
        spinner.setVisibility(View.VISIBLE);
        loadedImage.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (loadedImage.getVisibility() == View.INVISIBLE) {
            spinner.measure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(spinner.getMeasuredWidth(), spinner.getMeasuredHeight());
        } else {
            loadedImage.measure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(loadedImage.getMeasuredWidth(), loadedImage.getMeasuredHeight());
        }
    }
}
