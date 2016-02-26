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
import com.licon.rssfeeds.data.constants.RSSURL;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.adapter.TechnologyAdapter;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
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

public class TechnologyFragment extends Fragment implements TechnologyAdapter.onItemClickListener {
	private TechnologyAdapter mTechnologyAdapter;
	private List<FeedItem> mFeedsData;

	private RecyclerView mRecyclerView;
	private LinearLayout mLayoutErrorLoad;
	private TextViewRoboto mTextErrorLoad;
	private ProgressBar mProgressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_technology, null);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
		mLayoutErrorLoad = (LinearLayout) view.findViewById(R.id.layout_error_load);
		mTextErrorLoad = (TextViewRoboto) view.findViewById(R.id.text_error_reload);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_loading);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		mFeedsData = new ArrayList<>();

		loadData();
		mTechnologyAdapter = new TechnologyAdapter(getContext(), mFeedsData);
		mRecyclerView.setAdapter(mTechnologyAdapter);

		mTextErrorLoad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadData();
			}
		});

		return view;
	}

	@Override
	public void onItemClick(View view) {

	}

	private void showError() {
		mRecyclerView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		mLayoutErrorLoad.setVisibility(View.VISIBLE);
	}

	private void showLoading(){
		mRecyclerView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
		mLayoutErrorLoad.setVisibility(View.GONE);
	}

	void showContent(){
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mRecyclerView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mLayoutErrorLoad.setVisibility(View.GONE);
			}
		});
	}

	void addData(final FeedItem item){
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mFeedsData.add(item);
				mTechnologyAdapter.notifyDataSetChanged();
			}
		});
	}

	private void loadData() {
		showLoading();
		new AsyncTask<Void, Integer, Reader>() {

			@Override
			protected Reader doInBackground(Void... params) {
				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder().url(RSSURL.XML_URL_TECHNOLOGY).build();
				try {
					Response response = client.newCall(request).execute();
					Reader result = response.body().charStream();
					FeedParser feedParser = new FeedParser();

					feedParser.setOnFeedItemHandler(new FeedParser.FeedItemHandler() {
						@Override
						public void OnFeedItem(FeedParser feedParser, FeedItem item) {
							addData(item);
						}
					});

					showContent();
					XmlPullParser parser = Xml.newPullParser();

					try {
						parser.setInput(result);
						feedParser.parseFeed(parser);
					} catch (XmlPullParserException e) {
						showError();
					} catch (IOException e) {
						showError();
					} catch (FeedParser.UnknownFeedException e) {
						showError();
					}
				} catch (IOException e) {
					showError();
				}
				return null;
			}

		}.execute();
	}
}