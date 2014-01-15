package com.lolchair.lolchair;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@EBean
public class PostListAdapter extends BaseAdapter {

    @RestService
    LolchairRestClient restClient;

    List<Post>         posts = new ArrayList<Post>();

    @RootContext
    Context            context;

    @AfterInject
    void initAdapter() {
        loadPosts();
    }

    @Background
    void loadPosts() {
        addPosts(restClient.getRecentPosts().getPosts());
    }

    @UiThread
    void addPosts(List<Post> newPosts) {
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostView postView;
        if (convertView == null) {
            postView = PostView_.build(context);
        } else {
            postView = (PostView) convertView;
        }

        postView.bind(getItem(position));

        return postView;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
