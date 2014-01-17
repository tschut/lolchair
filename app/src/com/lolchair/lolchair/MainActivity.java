package com.lolchair.lolchair;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById
    ListView        postList;

    @Bean
    PostListAdapter adapter;

    @AfterViews
    void bindAdapter() {
        postList.setAdapter(adapter);
        postList.addHeaderView(new View(this), null, true);
        postList.addFooterView(new View(this), null, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
