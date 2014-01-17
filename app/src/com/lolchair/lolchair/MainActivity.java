package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
    private final class WaitForEndScrollListener implements OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                if (postList.getLastVisiblePosition() >= postList.getCount() - 1) {
                    Toast.makeText(getApplicationContext(), "bla", Toast.LENGTH_SHORT).show();
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

    @AfterViews
    void bindAdapter() {
        postList.setAdapter(adapter);
        postList.addHeaderView(new View(this), null, true);
        postList.addFooterView(new View(this), null, true);
        postList.setOnScrollListener(new WaitForEndScrollListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
