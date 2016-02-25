package com.licon.rssfeeds.ui.fragment;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licon.rssfeeds.R;

public class BlankFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_blank, null);
	}
}