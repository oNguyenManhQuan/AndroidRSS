package com.licon.rssfeeds.ui.adapter;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
import com.licon.rssfeeds.util.DateFormatUtil;
import com.licon.rssfeeds.util.UIUtil;

import java.util.List;

public class RssBaseAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM_HEADER = 1;
    private final int VIEW_ITEM_FOOTER = 0;

    private onItemClickListener mItemClickListener;
    private List<FeedItem> mFeeds;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Resources mResources;

    public RssBaseAdapter(Context context, List<FeedItem> feeds) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mFeeds = feeds;
        this.mResources = context.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            viewHolder = new SimpleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SimpleViewHolder) {
            SimpleViewHolder mHolder = (SimpleViewHolder)holder;
            final FeedItem feed = mFeeds.get(position);
            String title = feed.getTitle();
            String description = feed.getDescription();
            String published_on = String.format(mResources.getString(R.string.published_on),
                    DateFormatUtil.parseDateToString(feed.getPublicationDate()));
            String media_url = feed.getMediaURL();
            Drawable errorDrawable = mContext.getResources()
                    .getDrawable(R.drawable.bg_no_image_available);

            UIUtil.addTextToTextView(mHolder.mTextTitle, title);
            UIUtil.addTextToTextView(mHolder.mTextDescription, description);
            UIUtil.addTextToTextView(mHolder.mTextPublishedDate, published_on);
            UIUtil.loadImageViewFromUrl(mHolder.mImageThumbnail, media_url, errorDrawable);

            if (feed.isHistory()) {
                UIUtil.addDrawableToView(mHolder.mCardView,
                        mResources.getDrawable(R.drawable.bg_ripple_effect_white));
            } else {
                UIUtil.addDrawableToView(mHolder.mCardView,
                        mResources.getDrawable(R.drawable.bg_ripple_effect_new_item));
            }

            UIUtil.addDrawableToView(mHolder.mImageThumbnail,
                    mResources.getDrawable(R.drawable.bg_no_image_available));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, feed);
                    }
                }
            });
        } else {
            ProgressViewHolder mHolder = (ProgressViewHolder)holder;
            mHolder.mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mFeeds.get(position) != null ? VIEW_ITEM_HEADER : VIEW_ITEM_FOOTER;
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    public void setOnItemClickListener(final onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        protected TextViewRoboto mTextTitle;
        protected TextViewRoboto mTextDescription;
        protected TextViewRoboto mTextPublishedDate;
        protected ImageView mImageThumbnail;
        protected CardView mCardView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            this.mTextTitle = (TextViewRoboto) itemView.findViewById(R.id.text_title);
            this.mTextDescription = (TextViewRoboto) itemView.findViewById(R.id.text_description);
            this.mTextPublishedDate = (TextViewRoboto) itemView.findViewById(R.id.text_published_date);
            this.mImageThumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            this.mCardView = (CardView) itemView.findViewById(R.id.cardItem);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        protected ProgressBar mProgressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, FeedItem item);
    }
}