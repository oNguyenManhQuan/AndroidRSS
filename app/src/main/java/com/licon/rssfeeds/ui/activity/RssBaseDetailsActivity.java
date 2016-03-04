package com.licon.rssfeeds.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.IntentData;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
import com.licon.rssfeeds.util.DateFormatUtil;
import com.licon.rssfeeds.util.UIUtil;

/**
 * Created by FRAMGIA\khairul.alam.licon on 2/3/16.
 */
public class RssBaseDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ActionBar mActionbar;

    private ImageView mImageThumbnail;
    private TextViewRoboto mTextTitle;
    private TextViewRoboto mTextDescription;
    private TextViewRoboto mTextAuthor;
    private TextViewRoboto mTextPublishedDate;
    private Button mButtonLink;
    private Button mButtonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_details);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mImageThumbnail = (ImageView) findViewById(R.id.image_thumbnail);
        mTextTitle = (TextViewRoboto) findViewById(R.id.text_title);
        mTextDescription = (TextViewRoboto) findViewById(R.id.text_description);
        mTextAuthor = (TextViewRoboto) findViewById(R.id.text_author);
        mTextPublishedDate = (TextViewRoboto) findViewById(R.id.text_published_date);
        mButtonLink = (Button) findViewById(R.id.button_link);
        mButtonShare = (Button) findViewById(R.id.button_share);

        setupActionBar();
        getFeedViaIntent();
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        mActionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void getFeedViaIntent() {
        Intent intent = this.getIntent();
        FeedItem feedItem = (FeedItem) intent.getSerializableExtra(IntentData.DetailsData);
        setFeedDataOnUI(feedItem);
    }

    private void setFeedDataOnUI(FeedItem feedItem) {
        String title = feedItem.getTitle();
        String description = feedItem.getDescription();
        String author = feedItem.getAuthor();
        String category = feedItem.getCategory();
        String published_on = String.format(getResources().getString(R.string.published_on),
                DateFormatUtil.parseDateToString(feedItem.getPublicationDate()));
        String media_url = feedItem.getMediaURL();

        UIUtil.addTextToTextView(mTextTitle, title);
        UIUtil.addTextToTextView(mTextDescription, description);
        UIUtil.addTextToTextView(mTextPublishedDate, published_on);
        UIUtil.addTextToTextView(mTextAuthor, author);
        UIUtil.loadImageViewFromUrl(mImageThumbnail, media_url, this);

        mCollapsingToolbarLayout.setTitle(!TextUtils.isEmpty(category) ? category : "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}