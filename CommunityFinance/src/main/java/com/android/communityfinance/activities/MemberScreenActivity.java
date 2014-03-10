package com.android.communityfinance.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.communityfinance.R;
import com.android.communityfinance.ViewHelper;
import com.android.communityfinance.database.DatabaseHandler;
import com.android.communityfinance.domain.Member;


public class MemberScreenActivity extends FragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final int SAVE_MEMBER_DETAILS = 0;

    DatabaseHandler dbHandler;
    ArrayList<Member> members;
    ViewHelper viewHelper;
    View detailsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_screen);
        detailsContainer = findViewById(R.id.layout_member_details_container);
        dbHandler = new DatabaseHandler(this);
        members = dbHandler.getAllMembers(0);

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
        members = dbHandler.getAllMembers(0);
        if(members.size() > 0)
            viewHelper.populateMemberDetailsToView(detailsContainer, members.get(0));
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

        try
        {
            viewHelper.populateMemberDetailsToView(detailsContainer, members.get(i));
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
                Member newMember = new Member();
                viewHelper.populateMemberDetailsToView(findViewById(R.id.layout_member_details_container),newMember);
                Toast.makeText(this,"Add Member details and click on Save",Toast.LENGTH_SHORT).show();

                break;
        }
    }


    public void Reload()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
