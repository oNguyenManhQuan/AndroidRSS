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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.ui.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private FloatingActionButton mFabCategory;
    private PopupMenu mPopupMenu;

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
        mFabCategory = (FloatingActionButton) findViewById(R.id.fab_category);

        mFabCategory.setOnClickListener(this);

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

        addTab(R.string.rss_category_usa_home, R.drawable.ic_tab_usa_home, AppData.TAB_USA_HOME);
        addTab(R.string.rss_category_tech, R.drawable.ic_tab_tech, AppData.TAB_TECHNOLOGY);
        addTab(R.string.rss_category_business, R.drawable.ic_tab_business, AppData.TAB_BUSINESS);
        addTab(R.string.rss_category_health, R.drawable.ic_tab_health, AppData.TAB_HEALTH);
        addTab(R.string.rss_category_entertainment, R.drawable.ic_tab_entertainment, AppData.TAB_ENTERTAINMENT);
        addTab(R.string.rss_category_others, R.drawable.ic_tab_others, AppData.TAB_OTHERS);

        mTablayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        mTablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tabIndicator));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }

    private void addTab(int textResId, int iconResId, int position) {
        TabLayout.Tab tabLayout = mTablayout.newTab();
        tabLayout.setText(getResources().getString(textResId));
        tabLayout.setIcon(getResources().getDrawable(iconResId));
        mTablayout.addTab(tabLayout, position);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_category:
                showPopupMenu(view);
                break;
        }
    }

    public void showPopupMenu(View view) {
        mPopupMenu = new PopupMenu(MainActivity.this, view);
        mPopupMenu.setOnMenuItemClickListener(this);
        mPopupMenu.inflate(R.menu.menu_popup);
        mPopupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_home:
                mViewPager.setCurrentItem(AppData.TAB_USA_HOME);
                return true;
            case R.id.action_open_technology:
                mViewPager.setCurrentItem(AppData.TAB_TECHNOLOGY);
                return true;
            case R.id.action_open_business:
                mViewPager.setCurrentItem(AppData.TAB_BUSINESS);
                return true;
            case R.id.action_open_health:
                mViewPager.setCurrentItem(AppData.TAB_HEALTH);
                return true;
            case R.id.action_open_entertainment:
                mViewPager.setCurrentItem(AppData.TAB_ENTERTAINMENT);
                return true;
            case R.id.action_open_others:
                mViewPager.setCurrentItem(AppData.TAB_OTHERS);
                return true;
        }
        mPopupMenu.dismiss();
        return false;
    }
}