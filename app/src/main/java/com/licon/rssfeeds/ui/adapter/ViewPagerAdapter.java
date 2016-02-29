package com.licon.rssfeeds.ui.adapter;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.data.constants.RSSURL;
import com.licon.rssfeeds.ui.fragment.OthersFragment;
import com.licon.rssfeeds.ui.fragment.RssBaseFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUMBER_OF_TABS = 6;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;
        RssBaseFragment rssBaseFragment = new RssBaseFragment();

        switch (position) {
            case AppData.TAB_USA_HOME:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_USA_HOME);
                mFragment = rssBaseFragment;
                break;
            case AppData.TAB_TECHNOLOGY:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_TECHNOLOGY);
                mFragment = rssBaseFragment;
                break;
            case AppData.TAB_BUSINESS:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_BUSINESS);
                mFragment = rssBaseFragment;
                break;
            case AppData.TAB_HEALTH:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_HEALTH);
                mFragment = rssBaseFragment;
                break;
            case AppData.TAB_ENTERTAINMENT:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_ENTERTAINMENT);
                mFragment = rssBaseFragment;
                break;
            case AppData.TAB_OTHERS:
                mFragment = new OthersFragment();
                break;
            default:
                rssBaseFragment.setRssFeedUrl(RSSURL.XML_URL_USA_HOME);
                mFragment = rssBaseFragment;
        }
        return mFragment;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }
}