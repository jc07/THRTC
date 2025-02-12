package me.kevingleason.androidrtc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import me.kevingleason.androidrtc.R;

/**
 * Created by satish on 11/6/2015.
 */
public class MainActivity extends AppCompatActivity {
    //Intent intent = getIntent();
  // String uname = intent.getExtras().getString("uname");
        private DrawerLayout mDrawerLayout;
        private ListView mDrawerList;
        private ActionBarDrawerToggle mDrawerToggle;

        // nav drawer title
        private CharSequence mDrawerTitle;

        // used to store app title
        private CharSequence mTitle;

        // slide menu items
        private String[] navMenuTitles;
        private TypedArray navMenuIcons;

        private ArrayList<me.kevingleason.androidrtc.model.TRHItem> TRHItems;
        private me.kevingleason.androidrtc.adapters.TRHListAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.j_activity_main);

            mTitle = mDrawerTitle = getTitle();

            // load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

            // nav drawer icons from resources
            navMenuIcons = getResources()
                    .obtainTypedArray(R.array.nav_drawer_icons);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

            TRHItems = new ArrayList<me.kevingleason.androidrtc.model.TRHItem>();

            // adding nav drawer items to array
            // Home
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            // Find People
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            // Photos
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            // Communities, Will add a counter here
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            // Pages
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            // What's hot, We  will add a counter here
            TRHItems.add(new me.kevingleason.androidrtc.model.TRHItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));


            // Recycle the typed array
            navMenuIcons.recycle();

            mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            adapter = new me.kevingleason.androidrtc.adapters.TRHListAdapter(getApplicationContext(),
                    TRHItems);
            mDrawerList.setAdapter(adapter);

            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(mTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            }
        }

        /**
         * Slide menu item click listener
         */
        private class SlideMenuClickListener implements
                ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // display view for selected nav drawer item
                displayView(position);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // toggle nav drawer on selecting action bar app icon/title
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            // Handle action bar actions click
            switch (item.getItemId()) {
                case R.id.action_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        /* *
         * Called when invalidateOptionsMenu() is triggered
         */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // if nav drawer is opened, hide the action items
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }

        /**
         * Diplaying fragment view for selected nav drawer list item
         */
        private void displayView(int position) {
            // update the main content by replacing fragments
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    //Bundle bundle = new Bundle();

                   // bundle.putString("message", uname );
                    fragment = new VideocallFragment();
                    break;
                case 2:

                    fragment = new PatientdataFragment();
                    break;
                case 3:
                    fragment = new MessageFragment();
                    break;
                case 4:
                    fragment = new LogoutFragment();
                    break;
                case 5:
                    fragment = new DummyFragment();
                    break;

                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }

        @Override
        public void setTitle(CharSequence title) {
            mTitle = title;
            getSupportActionBar().setTitle(mTitle);
        }

        /**
         * When using the ActionBarDrawerToggle, you must call it during
         * onPostCreate() and onConfigurationChanged()...
         */

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggls
            mDrawerToggle.onConfigurationChanged(newConfig);
        }

    }



