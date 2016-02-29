package com.licon.rssfeeds.ui.fragment;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.adapter.RssBaseAdapter;
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

    private RecyclerView mRecyclerView;
    private LinearLayout mLayoutErrorLoad;
    private TextViewRoboto mTextErrorLoad;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss_base, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutErrorLoad = (LinearLayout) view.findViewById(R.id.layout_error_load);
        mTextErrorLoad = (TextViewRoboto) view.findViewById(R.id.text_error_reload);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_loading);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new LoadData().execute();
        mTextErrorLoad.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(View view) {

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
                        feedItems.add(item);
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
                mRssBaseAdapter = new RssBaseAdapter(getContext(), items);
                mRecyclerView.setAdapter(mRssBaseAdapter);
                mRssBaseAdapter.notifyDataSetChanged();
                showContent();
            } else {
                UIUtil.showErrorDialogNotify(getActivity(),
                        getString(R.string.text_dialog_title_error),
                        getString(R.string.text_dialog_msg_no_data),
                        getString(R.string.text_dialog_btn_ok));
            }
        }
    }

    public String getRssFeedUrl() {
        return mRssFeedUrl;
    }

    public void setRssFeedUrl(String mRssFeedUrl) {
        this.mRssFeedUrl = mRssFeedUrl;
    }
}