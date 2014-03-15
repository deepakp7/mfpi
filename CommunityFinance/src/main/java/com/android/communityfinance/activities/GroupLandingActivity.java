package com.android.communityfinance.activities;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.communityfinance.fragments.GroupDetailsFragment;
import com.android.communityfinance.fragments.MembersFragment;
import com.android.communityfinance.R;
import com.android.communityfinance.database.DatabaseHandler;
import com.android.communityfinance.domain.*;


public class GroupLandingActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    Group group;
    DatabaseHandler db_handler;

    // Three fragments that will go with three tabs
    MembersFragment fragment_members;
    GroupDetailsFragment fragment_group_details;

    public static final String INTENT_EXTRA_GROUP="GroupUID";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_landing);
        db_handler = new DatabaseHandler(getApplicationContext());


        int groupUID= getIntent().getIntExtra(INTENT_EXTRA_GROUP,0);
        group = db_handler.getGroup(groupUID);
        this.setTitle(group.GroupName);
        fragment_members = MembersFragment.newInstance(groupUID);
        fragment_group_details = GroupDetailsFragment.newInstance(groupUID);


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).]\
            switch(position)
            {
                case 0:
                    return fragment_group_details;
                case 1:
                    return fragment_members;
            }
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /*public static class MembersFragment1 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

        Activity activity;
        DatabaseHandler dbHandler;
        ArrayList<Member> members;
        View detailsContainer;
        int groupUID;

        public static final String ARG_PARAM1 = "Group UID";

        // Always use this factory method to instantiate
        public static MembersFragment1 newInstance(int groupUID) {
            MembersFragment1 fragment = new MembersFragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_PARAM1, groupUID);
            fragment.setArguments(args);
            return fragment;
        }

        public MembersFragment1() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                groupUID = getArguments().getInt(ARG_PARAM1);
            }

            activity = getActivity();
            dbHandler = new DatabaseHandler(activity.getApplicationContext());
            members = dbHandler.getAllMembers(groupUID);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_members, container, false);
        }

        @Override
        public void onStart()
        {
            super.onStart();
            detailsContainer = activity.findViewById(R.id.layout_member_details_container);
            Button addMemberButton = (Button) activity.findViewById(R.id.button_add_member);
            addMemberButton.setOnClickListener(this);

            ListView lv = (ListView) activity.findViewById(R.id.listview_member_names);
            ArrayAdapter adapter = new ArrayAdapter(activity,android.R.layout.simple_list_item_1,members);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.button_add_member:
                    Member newMember = new Member();
                    ViewHelper.populateMemberDetailsToView(activity.findViewById(R.id.layout_member_details_container), newMember);
                    Toast.makeText(activity, "Add Member details and click on Save", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_save_member:
                    Member updatedMember = ViewHelper.fetchMemberDetailsFromView(getActivity().findViewById(R.id.layout_member_details_container));
                    updatedMember.GroupUID = groupUID;
                    dbHandler.addUpdateMember(updatedMember);
                    Toast.makeText(getActivity(),"Details saved",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try
            {
                ViewHelper.populateMemberDetailsToView(detailsContainer, members.get(i));
            }
            catch (Exception ex)
            {
                Log.d("Exception", ex.getMessage());
            }
        }

    } */
}
