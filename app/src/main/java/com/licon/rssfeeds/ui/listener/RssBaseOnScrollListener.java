package com.licon.rssfeeds.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.licon.rssfeeds.data.constants.AppData;

/**
 * Created by FRAMGIA\khairul.alam.licon on 8/3/16.
 */

public abstract class RssBaseOnScrollListener extends RecyclerView.OnScrollListener {
    private int mVisibleThreshold = AppData.PAGINATION_RECYCLER_THRESHOLD;
    private int mLastVisibleItem, mTotalItemCount;
    private boolean mLoading;
    private LinearLayoutManager mLinearLayoutManager;

    public RssBaseOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (!mLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
            onLoadMore();
            mLoading = true;
        }
    }

    public abstract void onLoadMore();
}