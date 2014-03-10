package com.android.communityfinance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.android.communityfinance.R;
import com.android.communityfinance.database.DatabaseHandler;
import com.android.communityfinance.domain.Group;

import java.util.ArrayList;


public class GroupsGridActivity extends Activity implements AdapterView.OnItemClickListener {

    DatabaseHandler db_handler;
    ArrayList<Group> groups;
    ArrayAdapter adapter_grid;

    public static final int REQUEST_ADD_GROUP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_grid);
        Button addGroupButton =(Button) findViewById(R.id.button_add_group);

        db_handler = new DatabaseHandler(getApplicationContext());

        groups = new ArrayList<Group>();

        if(addGroupButton != null)
        {
            addGroupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),AddGroupActivity.class);
                    startActivityForResult(intent,REQUEST_ADD_GROUP);
                }
            });
        }

        GridView gv = (GridView) findViewById(R.id.layout_groups_grid);
        adapter_grid = new ArrayAdapter(this,android.R.layout.simple_list_item_1,groups);
        gv.setAdapter(adapter_grid);
        gv.setOnItemClickListener(this);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        groups = db_handler.getAllGroups();
        adapter_grid.clear();
        adapter_grid.addAll(groups);
        adapter_grid.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groups_grid, menu);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), GroupLandingActivity.class);
        intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP,groups.get(i).UID);
        startActivity(intent);
    }
}
