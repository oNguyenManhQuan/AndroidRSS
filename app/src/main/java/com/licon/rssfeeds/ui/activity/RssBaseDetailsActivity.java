package com.licon.rssfeeds.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itextpdf.text.DocumentException;
import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.IntentData;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
import com.licon.rssfeeds.util.AppUtil;
import com.licon.rssfeeds.util.DateFormatUtil;
import com.licon.rssfeeds.util.FileUtil;
import com.licon.rssfeeds.util.RssNewsUtil;
import com.licon.rssfeeds.util.UIUtil;

import java.io.FileNotFoundException;

/**
 * Created by FRAMGIA\khairul.alam.licon on 2/3/16.
 */
public class RssBaseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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
    private Button mButtonPrint;

    private String mLink;

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
        mButtonPrint = (Button) findViewById(R.id.button_print);

        mButtonLink.setOnClickListener(this);
        mButtonShare.setOnClickListener(this);
        mButtonPrint.setOnClickListener(this);

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
        if (intent != null && intent.getExtras() != null) {
            FeedItem feedItem = (FeedItem) intent.getSerializableExtra(IntentData.DETAILS_DATA);
            setFeedDataOnUI(feedItem);
        }
    }

    private void setFeedDataOnUI(FeedItem feedItem) {
        String title = feedItem.getTitle();
        String description = feedItem.getDescription();
        String author = feedItem.getAuthor();
        String category = feedItem.getCategory();
        String published_on = String.format(getResources().getString(R.string.published_on),
                DateFormatUtil.parseDateToString(feedItem.getPublicationDate()));
        String media_url = feedItem.getMediaURL();
        Drawable errorDrawable = getApplicationContext().getResources()
                .getDrawable(R.drawable.bg_banar_voa);

        UIUtil.addTextToTextView(mTextTitle, title);
        UIUtil.addTextToTextView(mTextDescription, description);
        UIUtil.addTextToTextView(mTextPublishedDate, published_on);
        UIUtil.addTextToTextView(mTextAuthor, author);
        UIUtil.loadImageViewFromUrl(mImageThumbnail, media_url, errorDrawable);

        mCollapsingToolbarLayout.setTitle(!TextUtils.isEmpty(category) ? category : "");
        setLink(feedItem.getLink());
        RssNewsUtil.generateNews(title, published_on, description, media_url, getLink());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_link:
                AppUtil.openBrowser(getApplicationContext(), getLink());
                break;
            case R.id.button_print:
                    try {
                        FileUtil.createPdfToPrint(RssBaseDetailsActivity.this,
                                RssNewsUtil.getNewsBuilder().toString());
                    } catch (DocumentException e) {
                        showExceptionMessage(e.getMessage());
                    } catch (FileNotFoundException e) {
                        showExceptionMessage(e.getMessage());
                    }
                break;
            case R.id.button_share:
                AppUtil.shareDataUsingIntent(RssNewsUtil.getNewsBuilder().toString(),
                        getLink(), RssBaseDetailsActivity.this);
                break;
        }
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    private void showExceptionMessage(String message) {
        UIUtil.showDialogNotify(RssBaseDetailsActivity.this,
                getString(R.string.text_dialog_title_error),
                message,
                null,
                getString(R.string.text_dialog_btn_ok),
                null,
                UIUtil.getDefaultDismissListener());
    }
}