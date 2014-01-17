package com.lolchair.lolchair;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@EViewGroup(R.layout.post_view)
public class PostView extends LinearLayout {

    @ViewById
    TextView        title;

    @ViewById
    ImageView       image;

    private Context context;

    public PostView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(Post post) {
        title.setText(post.title);
        Picasso.with(context).load(post.thumbnail_images.full.url.toString()).into(image);
    }
}
