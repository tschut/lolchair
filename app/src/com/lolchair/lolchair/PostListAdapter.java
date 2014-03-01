package com.lolchair.lolchair;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@EBean
public class PostListAdapter extends BaseAdapter {

    private static final int     POSTS_PER_REQUEST = 5;

    @RestService
    LolchairRestClient           restClient;

    private List<Post>           posts             = new ArrayList<Post>();

    private int                  page              = 1;
    private boolean              isLoading         = false;

    @RootContext
    Context                      context;

    private INoMorePagesCallback callback;

    public void initAdapter(INoMorePagesCallback callback) {
        this.callback = callback;
        loadPosts(false);
    }

    @Background
    void loadPosts(boolean nextPage) {
        if (!isLoading) {
            isLoading = true;
            if (nextPage) {
                page++;
            }
            try {
                PostsReply postsReply = restClient.getRecentPosts(POSTS_PER_REQUEST, page);
                addPosts(postsReply.posts);
                if (postsReply.pages == page) {
                    callback.noMorePosts();
                }
            } catch (RestClientException exception) {
                exception.printStackTrace();
            } finally {
                isLoading = false;
            }
        }
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

    public void nextPage() {
        loadPosts(true);
    }
}
