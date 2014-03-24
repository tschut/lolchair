package com.lolchair.lolchair;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends Activity implements INoMorePagesCallback {
    private static final int ACTION_SUBMIT_GALLERY = 0;
    private static final int ACTION_SUBMIT_CAMERA  = 1;

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
    private String  currentPicturePath;

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

    @OptionsItem
    void menuSubmitGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ACTION_SUBMIT_GALLERY);
    }

    @OptionsItem
    void menuSubmitCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            // TODO handle error
            e.printStackTrace();
        }
        if (photoFile != null) {
            currentPicturePath = photoFile.getAbsolutePath();
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePicture, ACTION_SUBMIT_CAMERA);
        }
    }

    @OnActivityResult(ACTION_SUBMIT_GALLERY)
    void submitPictureGallery(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Intent submitActivityIntent = SubmitActivity_.intent(this).get();
            submitActivityIntent.putExtra(SubmitActivity.IMAGE_URI, intent.getData().toString());
            startActivity(submitActivityIntent);
        }
    }

    @OnActivityResult(ACTION_SUBMIT_CAMERA)
    void submitPictureCamera(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Intent submitActivityIntent = SubmitActivity_.intent(this).get();
            submitActivityIntent.putExtra(SubmitActivity.IMAGE_PATH, currentPicturePath);
            startActivity(submitActivityIntent);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    @UiThread
    public void noMorePosts() {
        postList.removeFooterView(footerView);
    }
}
