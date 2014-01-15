package com.lolchair.lolchair;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@EViewGroup(R.layout.post_view)
public class PostView extends LinearLayout {

    @ViewById
    TextView  title;

    @ViewById
    TextView  text;

    @ViewById
    ImageView image;

    public PostView(Context context) {
        super(context);
    }

    public void bind(Post post) {
        title.setText(post.getTitle());
        text.setText(Html.fromHtml(post.getExcerpt()));
    }
}
