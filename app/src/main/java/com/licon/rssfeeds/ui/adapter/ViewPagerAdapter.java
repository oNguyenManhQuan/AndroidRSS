package com.licon.rssfeeds.ui.adapter;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.licon.rssfeeds.RssBaseApplication;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.data.constants.RSSData;
import com.licon.rssfeeds.ui.fragment.OthersFragment;
import com.licon.rssfeeds.ui.fragment.RssBaseFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUMBER_OF_TABS = 6;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        RssBaseFragment rssBaseFragment = new RssBaseFragment();
        switch (position) {
            case AppData.TAB_USA_HOME:
                rssBaseFragment.setRssFeedUrl(RSSData.XML_URL_USA_HOME);
                rssBaseFragment.setCategory(RSSData.ATTRIBUTE_CAT_USA_HOME);
                return rssBaseFragment;
            case AppData.TAB_TECHNOLOGY:
                rssBaseFragment.setRssFeedUrl(RSSData.XML_URL_TECHNOLOGY);
                rssBaseFragment.setCategory(RSSData.ATTRIBUTE_CAT_TECHNOLOGY);
                return rssBaseFragment;
            case AppData.TAB_BUSINESS:
                rssBaseFragment.setRssFeedUrl(RSSData.XML_URL_BUSINESS);
                rssBaseFragment.setCategory(RSSData.ATTRIBUTE_CAT_BUSINESS);
                return rssBaseFragment;
            case AppData.TAB_HEALTH:
                rssBaseFragment.setRssFeedUrl(RSSData.XML_URL_HEALTH);
                rssBaseFragment.setCategory(RSSData.ATTRIBUTE_CAT_HEALTH);
                return rssBaseFragment;
            case AppData.TAB_ENTERTAINMENT:
                rssBaseFragment.setRssFeedUrl(RSSData.XML_URL_ENTERTAINMENT);
                rssBaseFragment.setCategory(RSSData.ATTRIBUTE_CAT_ENTERTAINMENT);
                return rssBaseFragment;
            case AppData.TAB_OTHERS:
                return new OthersFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case AppData.TAB_USA_HOME:
                return RssBaseApplication.TAB_USA_HOME;
            case AppData.TAB_TECHNOLOGY:
                return RssBaseApplication.TAB_TECHNOLOGY;
            case AppData.TAB_BUSINESS:
                return RssBaseApplication.TAB_BUSINESS;
            case AppData.TAB_HEALTH:
                return RssBaseApplication.TAB_HEALTH;
            case AppData.TAB_ENTERTAINMENT:
                return RssBaseApplication.TAB_ENTERTAINMENT;
            case AppData.TAB_OTHERS:
                return RssBaseApplication.TAB_OTHERS;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }
}