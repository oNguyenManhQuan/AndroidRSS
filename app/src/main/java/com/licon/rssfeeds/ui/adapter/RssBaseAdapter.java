package com.licon.rssfeeds.ui.adapter;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.RssBaseApplication;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.ui.widget.TextViewRoboto;
import com.licon.rssfeeds.util.DateFormatUtil;
import com.licon.rssfeeds.util.UIUtil;

import java.util.List;

public class RssBaseAdapter extends RecyclerView.Adapter<RssBaseAdapter.SimpleViewHolder> {

    private onItemClickListener mItemClickListener;
    private List<FeedItem> mFeeds;
    private LayoutInflater mLayoutInflater;
    private int mCount;
    private Context mContext;

    public RssBaseAdapter(Context context, List<FeedItem> feeds) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mFeeds = feeds;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.item_feed, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        final FeedItem feed = mFeeds.get(position);

        String title = feed.getTitle();
        String description = feed.getDescription();
        String published_on = String.format(mContext.getResources().getString(R.string.published_on),
                DateFormatUtil.parseDateToString(feed.getPublicationDate()));

        UIUtil.addTextToTextView(holder.mTextTitle, title);
        UIUtil.addTextToTextView(holder.mTextDescription, description);
        UIUtil.addTextToTextView(holder.mTextPublishedDate, published_on);

        if (feed.isHistory()) {
            UIUtil.addDrawableToView(holder.mCardView, mContext.getResources().getDrawable(R.drawable.bg_ripple_effect_white));
        } else {
            UIUtil.addDrawableToView(holder.mCardView, mContext.getResources().getDrawable(R.drawable.bg_ripple_effect_new_item));
        }

        UIUtil.addDrawableToView(holder.mImageThumbnail, mContext.getResources().getDrawable(R.drawable.bg_no_image_available));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, feed);
                }
            }
        });
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

    public interface onItemClickListener {
        void onItemClick(View view, FeedItem item);
    }
}