package com.licon.rssfeeds.ui.fragment;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.data.constants.IntentData;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.database.constants.DBConfig;
import com.licon.rssfeeds.ui.activity.RssBaseDetailsActivity;
import com.licon.rssfeeds.ui.adapter.RssBaseAdapter;
import com.licon.rssfeeds.ui.listener.RssBaseOnScrollListener;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
import com.licon.rssfeeds.util.UIUtil;
import com.licon.rssfeeds.util.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RssBaseFragment extends Fragment implements RssBaseAdapter.onItemClickListener,
        View.OnClickListener {

    private RssBaseAdapter mRssBaseAdapter;
    private String mRssFeedUrl;
    private List<FeedItem> mFeedItemsAll;
    private List<FeedItem> mFeedItemsPaginated;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private LinearLayout mLayoutErrorLoad;
    private TextViewRoboto mTextErrorLoad;
    private ProgressBar mProgressBar;
    private Context mContext;

    // pagination
    private int mLimit = AppData.PAGINATION_DEFAULT_LIMIT;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss_base, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutErrorLoad = (LinearLayout) view.findViewById(R.id.layout_error_load);
        mTextErrorLoad = (TextViewRoboto) view.findViewById(R.id.text_error_reload);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_loading);

        mTextErrorLoad.setOnClickListener(this);
        mContext = getActivity().getApplicationContext();

        mFeedItemsAll = new ArrayList<>();
        mFeedItemsPaginated = new ArrayList<>();
        new LoadData().execute();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRssBaseAdapter = new RssBaseAdapter(mContext, mFeedItemsPaginated);
        mRecyclerView.setAdapter(mRssBaseAdapter);
        mRssBaseAdapter.setOnItemClickListener(RssBaseFragment.this);

        mRecyclerView.setOnScrollListener(new RssBaseOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, FeedItem feedItem) {
        Intent intent = new Intent(getActivity(), RssBaseDetailsActivity.class);
        intent.putExtra(IntentData.DETAILS_DATA, feedItem);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_error_reload:
                new LoadData().execute();
                break;
        }
    }

    private void showErrorToReload() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mLayoutErrorLoad.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLayoutErrorLoad.setVisibility(View.GONE);
    }

    void showContent() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mLayoutErrorLoad.setVisibility(View.GONE);
    }

    private class LoadData extends AsyncTask<Void, String, List<FeedItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected List<FeedItem> doInBackground(Void... params) {

            DBConfig.getConfig().feedItemSQLiteHelper.deleteXDaysOldFeeds();  //  to synchronize rss news list

            final List<FeedItem> feedItems = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(getRssFeedUrl()).build();
            try {
                Response response = client.newCall(request).execute();
                Reader result = response.body().charStream();
                FeedParser feedParser = new FeedParser();

                feedParser.setOnFeedItemHandler(new FeedParser.FeedItemHandler() {
                    @Override
                    public void OnFeedItem(FeedParser feedParser, FeedItem item) {

                        FeedItem historyItem = DBConfig.getConfig().feedItemSQLiteHelper.getSingleFeedItem(
                                item.getTitle(),
                                item.getCategory());

                        //adding old data to view from db history
                        if(historyItem != null) {
                            historyItem.setHistory(true);
                            feedItems.add(historyItem);
                        //adding new data to view from web
                        } else {
                            DBConfig.getConfig().feedItemSQLiteHelper.addRssFeed(item);
                            item.setHistory(false);
                            feedItems.add(item);
                        }
                    }
                });

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(result);
                feedParser.parseFeed(parser);
            } catch (IOException e) {
                return null;
            } catch (XmlPullParserException xmlPullParserException) {
                return null;
            } catch (FeedParser.UnknownFeedException unknownFeedException) {
                return null;
            }

            return feedItems;
        }

        @Override
        protected void onPostExecute(List<FeedItem> items) {
            if(!items.isEmpty()) {
                if(mContext != null) {
                    mFeedItemsAll = items;
                    loadDefaultData();
                    showContent();
                }
            } else {
                UIUtil.showErrorDialogNotify(getActivity(),
                        getString(R.string.text_dialog_title_error),
                        getString(R.string.text_dialog_msg_no_data),
                        getString(R.string.text_dialog_btn_ok));
            }
        }
    }

    private void loadDefaultData() {
        try {
            for (int i = 0; i <= mLimit; i++) {
                mFeedItemsPaginated.add(mFeedItemsAll.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            UIUtil.showErrorDialogNotify(getActivity(),
                    getString(R.string.text_dialog_title_sorry),
                    getString(R.string.text_dialog_msg_no_more_data),
                    getString(R.string.text_dialog_btn_ok));
        }
    }

    private void loadMoreData() {
        mHandler = new Handler();
        mFeedItemsPaginated.add(null);
        mRssBaseAdapter.notifyItemInserted(mFeedItemsPaginated.size() - 1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFeedItemsPaginated.remove(mFeedItemsPaginated.size() - 1);
                mRssBaseAdapter.notifyItemRemoved(mFeedItemsPaginated.size());
                int start = mFeedItemsPaginated.size();
                int end = start + mLimit;
                try {
                    for (int i = start; i <= end; i++) {
                        mFeedItemsPaginated.add(mFeedItemsAll.get(i));
                        mRssBaseAdapter.notifyItemInserted(mFeedItemsPaginated.size());
                    }
                } catch (IndexOutOfBoundsException e) {
                    UIUtil.showErrorDialogNotify(getActivity(),
                            getString(R.string.text_dialog_title_sorry),
                            getString(R.string.text_dialog_msg_no_more_data),
                            getString(R.string.text_dialog_btn_ok));
                }
            }
        }, AppData.PAGINATION_TIME_OUT);
    }

    public String getRssFeedUrl() {
        return mRssFeedUrl;
    }

    public void setRssFeedUrl(String mRssFeedUrl) {
        this.mRssFeedUrl = mRssFeedUrl;
    }
}