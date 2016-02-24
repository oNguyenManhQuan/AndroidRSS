package com.licon.rssfeeds.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.ui.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private CollapsingToolbarLayout mCollapsingToolbarLayout;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;
	private Toolbar mToolbar;
	private ViewPager mViewPager;
	private TabLayout mTablayout;
	private FloatingActionButton mFabCategory;

	private ActionBar mActionbar;
	private ActionBarDrawerToggle mDrawerToggle;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mTablayout = (TabLayout) findViewById(R.id.tablayout);

		setupActionBar();
		setupViewPagerWithTabs();
		mNavigationView.setNavigationItemSelectedListener(this);
	}

	private void setupActionBar() {
		setSupportActionBar(mToolbar);
		mActionbar = getSupportActionBar();
		mActionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
		mActionbar.setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
				R.string.action_open, R.string.action_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	private void setupViewPagerWithTabs() {
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mTablayout.setupWithViewPager(mViewPager);

		//tab-1
		final TabLayout.Tab tab_tech = mTablayout.newTab();
		tab_tech.setText(getResources().getString(R.string.rss_category_tech));
		tab_tech.setIcon(getResources().getDrawable(R.drawable.ic_tab_tech));
		mTablayout.addTab(tab_tech, AppData.TAB_TECHNOLOGY);
		//tab-2
		final TabLayout.Tab tab_business = mTablayout.newTab();
		tab_business.setText(getResources().getString(R.string.rss_category_business));
		tab_business.setIcon(getResources().getDrawable(R.drawable.ic_tab_business));
		mTablayout.addTab(tab_business, AppData.TAB_BUSINESS);
		//tab-3
		final TabLayout.Tab tab_finance = mTablayout.newTab();
		tab_finance.setText(getResources().getString(R.string.rss_category_finance));
		tab_finance.setIcon(getResources().getDrawable(R.drawable.ic_tab_finance));
		mTablayout.addTab(tab_finance, AppData.TAB_FINANCE);
		//tab-4
		final TabLayout.Tab tab_travel = mTablayout.newTab();
		tab_travel.setText(getResources().getString(R.string.rss_category_travel));
		tab_travel.setIcon(getResources().getDrawable(R.drawable.ic_tab_travel));
		mTablayout.addTab(tab_travel, AppData.TAB_TRAVEL);
		//tab-5
		final TabLayout.Tab tab_others = mTablayout.newTab();
		tab_others.setText(getResources().getString(R.string.rss_category_others));
		tab_others.setIcon(getResources().getDrawable(R.drawable.ic_tab_others));
		mTablayout.addTab(tab_others, AppData.TAB_OTHERS);

		mTablayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
		mTablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tabIndicator));

		mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.nav_item_feedstab:

				break;
			case R.id.nav_item_settings:

				break;
			case R.id.nav_item_help:

				break;
		}
		mDrawerLayout.closeDrawers();
		return false;
	}
}