package com.licon.rssfeeds.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.ui.fragment.BlankFragment;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private static final int NUMBER_OF_TABS = 5;

	public ViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch(position) {
			case AppData.TAB_TECHNOLOGY:
				fragment = new BlankFragment();
				break;
			case AppData.TAB_BUSINESS:
				fragment = new BlankFragment();
				break;
			case AppData.TAB_FINANCE:
				fragment = new BlankFragment();
				break;
			case AppData.TAB_TRAVEL:
				fragment = new BlankFragment();
				break;
			case AppData.TAB_OTHERS:
				fragment = new BlankFragment();
				break;
			default:
				fragment = new BlankFragment();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return NUMBER_OF_TABS;
	}
}