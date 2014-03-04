package com.android.communityfinance;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.communityfinance.domain.Member;


public class MemberScreenActivity extends FragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final int SAVE_MEMBER_DETAILS = 0;

    DatabaseHandler dbHandler;
    ArrayList<Member> members;
    ViewHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_screen);

        dbHandler = new DatabaseHandler(this);
        members = dbHandler.getAllMembers();

        Button addMemberButton = (Button) findViewById(R.id.button_add_member);
        addMemberButton.setOnClickListener(this);

        ListView lv = (ListView) findViewById(R.id.listview_member_names);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,members);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }

    @Override protected void onResume()
    {
        super.onResume();
        members = dbHandler.getAllMembers();
        if(members.size() > 0)
            viewHelper.populateMemberDetailsToView(findViewById(R.id.layout_member_details_container), members.get(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.member_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonmember, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        View detailsContainer = findViewById(R.id.layout_member_details_container);
        try
        {
            if(detailsContainer == null)
            {
                Intent intent = new Intent(this,DetailsActivity.class);
                Member member = members.get(i);
                intent.putExtra("FirstName",member.FirstName);
                intent.putExtra("LastName",member.LastName);
                intent.putExtra("ContactInfo",member.ContactInfo);
                startActivityForResult(intent, SAVE_MEMBER_DETAILS);
            }
            else
            {
                viewHelper.populateMemberDetailsToView(findViewById(R.id.layout_member_details_container), members.get(i));
            }
        }
        catch (Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_member:
                if(getSupportFragmentManager().findFragmentById(R.id.fragment_member_details) != null)
                {
                    Member newMember = new Member();
                    viewHelper.populateMemberDetailsToView(findViewById(R.id.layout_member_details_container),newMember);
                    Toast.makeText(this,"Add Member details and click on Save",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(this,DetailsActivity.class);
                    Member member = new Member();
                    intent.putExtra("UID",member.UID);
                    intent.putExtra("FirstName",member.FirstName);
                    intent.putExtra("LastName",member.LastName);
                    intent.putExtra("ContactInfo",member.ContactInfo);
                    startActivityForResult(intent, SAVE_MEMBER_DETAILS);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if (requestCode == SAVE_MEMBER_DETAILS) {
            if (resultCode == MemberDetailsFragment.RESULT_SAVED) {
                // Member details were saved, refresh
                Reload();
            }
        }
    }

    public void Reload()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    // Activity that will be called in case screen is not enough to fit both fragments
    public static class DetailsActivity extends FragmentActivity {

        Member fragment_member;
        MemberDetailsFragment details_fragment;
        ViewHelper viewHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            viewHelper = new ViewHelper();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }

            fragment_member = new Member();
            String uid_string = getIntent().getStringExtra("UID");
            if(uid_string != null && ! uid_string.isEmpty()) fragment_member.UID=Integer.parseInt(uid_string);
            fragment_member.FirstName=getIntent().getStringExtra("FirstName");
            fragment_member.LastName=getIntent().getStringExtra("LastName");
            fragment_member.ContactInfo=getIntent().getStringExtra("ContactInfo");

            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment.

                // create fragment
                details_fragment = new MemberDetailsFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, details_fragment).commit();
            }
        }

        @Override
        protected void onResume()
        {
            super.onResume();
            ViewHelper.populateMemberDetailsToView(findViewById(R.id.layout_member_details_container), fragment_member);
        }
    }

}
