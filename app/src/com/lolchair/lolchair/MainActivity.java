package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends Activity implements INoMorePagesCallback {
    private final class WaitForEndScrollListener implements OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                if (postList.getLastVisiblePosition() >= postList.getCount() - 3) {
                    adapter.nextPage();
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    @ViewById
    ListView        postList;

    @Bean
    PostListAdapter adapter;

    private View    footerView;

    @AfterViews
    void bindAdapter() {
        footerView = getLayoutInflater().inflate(R.layout.footer_view, null);
        postList.addFooterView(footerView, null, false);
        postList.addHeaderView(new View(this), null, false);
        postList.setOnScrollListener(new WaitForEndScrollListener());
        postList.setAdapter(adapter);
        adapter.initAdapter(this);
    }

    @ItemClick(R.id.postList)
    void postItemClicked(Post post) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.url.toString()));
        startActivity(browserIntent);
    }

    @OptionsItem
    void menuAbout() {
        AboutActivity_.intent(this).start();
    }

    @Override
    @UiThread
    public void noMorePosts() {
        postList.removeFooterView(footerView);
    }
}
