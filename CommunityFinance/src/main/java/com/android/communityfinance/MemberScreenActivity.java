package com.android.communityfinance;

import java.util.ArrayList;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.communityfinance.domain.Member;


public class MemberScreenActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    ArrayList<Member> members;
    MemberDetailsFragment details_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_screen);


        members = new ArrayList<Member>();

        Member member1 = new Member();
        member1.FirstName="Shashank";
        member1.LastName="Singh";
        member1.ContactInfo ="shashanksingh28@gmail.com";
        members.add(member1);

        Member member2 = new Member();
        member2.FirstName="Sidharth";
        member2.LastName="Sharma";
        member2.ContactInfo ="sidharth.sharma@gmail.com";
        members.add(member2);

        ListView lv = (ListView) findViewById(R.id.listview_member_names);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,members);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }

    @Override protected void onResume()
    {
        super.onResume();
        Log.d("CALLING POPULATE","For "+members.get(0).FirstName);
        PopulateDetails(findViewById(R.id.layout_member_details_container),members.get(0));
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
                startActivity(intent);
            }
            else
            {
                PopulateDetails(findViewById(R.id.layout_member_details_container),members.get(i));
            }
        }
        catch (Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }
    }

    public static void PopulateDetails(View view_member_container,Member memberToPopulate)
    {
        if(view_member_container == null) return;

        View view = view_member_container.findViewById(R.id.layout_member_details);

        if(view == null || memberToPopulate == null) return;

        TextView memberIdText = (TextView) view.findViewById(R.id.layout_member_uid);
        if(memberIdText != null) memberIdText.setText(memberToPopulate.UID);
        Log.d("test",memberIdText.getText().toString());

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null) firstNameEditor.setText(memberToPopulate.FirstName);
        Log.d("test",firstNameEditor.getText().toString());

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null) lastNameEditor.setText(memberToPopulate.LastName);

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null) contactEditor.setText(memberToPopulate.ContactInfo);

        view.invalidate();
    }

    // Activity that will be called in case screen is not enough to fit both fragments
    public static class DetailsActivity extends FragmentActivity {

        Member fragment_member;
        MemberDetailsFragment details_fragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }

            fragment_member = new Member();
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
            PopulateDetails(this.findViewById(R.id.layout_member_details_container),fragment_member);
        }
    }

}
