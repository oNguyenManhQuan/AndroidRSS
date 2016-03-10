package com.licon.rssfeeds.ui.fragment;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.RSSData;

public class OthersFragment extends Fragment implements View.OnClickListener {

    private Button mButtonAfrica;
    private Button mButtonAsia;
    private Button mButtonMiddleEast;
    private Button mButtonEurope;
    private NestedScrollView mNestedScrollView;
    private RssBaseFragment mRssBaseFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, null);

        mButtonAfrica = (Button) view.findViewById(R.id.button_africa);
        mButtonAsia = (Button) view.findViewById(R.id.button_asia);
        mButtonMiddleEast = (Button) view.findViewById(R.id.button_middle_east);
        mButtonEurope = (Button) view.findViewById(R.id.button_europe);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);

        mButtonAfrica.setOnClickListener(this);
        mButtonAsia.setOnClickListener(this);
        mButtonMiddleEast.setOnClickListener(this);
        mButtonEurope.setOnClickListener(this);

        return view;
    }

    private void replaceOldFragmentWithFeed(String rss_url, String category) {
        RssBaseFragment rssBaseFragment = new RssBaseFragment();
        rssBaseFragment.setRssFeedUrl(rss_url);
        rssBaseFragment.setCategory(category);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.layout_root, rssBaseFragment);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_africa:
                replaceOldFragmentWithFeed(RSSData.XML_URL_AFRICA, RSSData.ATTRIBUTE_CAT_AFRICA);
                break;
            case R.id.button_asia:
                replaceOldFragmentWithFeed(RSSData.XML_URL_ASIA, RSSData.ATTRIBUTE_CAT_ASIA);
                break;
            case R.id.button_middle_east:
                replaceOldFragmentWithFeed(RSSData.XML_URL_MIDDLE_EAST, RSSData.ATTRIBUTE_CAT_MIDDLE_EAST);
                break;
            case R.id.button_europe:
                replaceOldFragmentWithFeed(RSSData.XML_URL_EUROPE, RSSData.ATTRIBUTE_CAT_EUROPE);
                break;
        }
    }
}