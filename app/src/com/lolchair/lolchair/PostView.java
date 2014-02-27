package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@EViewGroup(R.layout.post_view)
public class PostView extends LinearLayout {

    @ViewById
    TextView         title;

    @ViewById
    LoadingImageView image;

    @ViewById
    RatingBar        rating;

    @ViewById
    Button           rateButton;

    private Context  context;

    public PostView(Context context) {
        super(context);
        this.context = context;
    }

    @AfterViews
    void initRatingBar() {
        rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    rateButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Click(R.id.rateButton)
    void rateButtonClicked() {

    }

    public void bind(Post post) {
        title.setText(Html.fromHtml(post.title));
        Picasso.with(context).load(post.thumbnail_images.full.url.toString()).into(image);
        rating.setRating(getPostRating(post));
    }

    private float getPostRating(Post post) {
        if (post.custom_fields.rating != null && post.custom_fields.rating[0] != null) {
            return Float.parseFloat(post.custom_fields.rating[0]);
        }
        return 0;
    }
}
