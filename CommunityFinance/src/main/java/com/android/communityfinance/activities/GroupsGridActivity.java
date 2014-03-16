package com.android.communityfinance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.communityfinance.R;
import com.android.communityfinance.SyncHelper;
import com.android.communityfinance.database.DatabaseHandler;
import com.android.communityfinance.domain.Group;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class GroupsGridActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    DatabaseHandler db_handler;
    ArrayList<Group> groups;
    ArrayAdapter adapter_grid;
    private ProgressDialog pDialog;

    public static final int REQUEST_ADD_GROUP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_grid);
        db_handler = new DatabaseHandler(getApplicationContext());

        groups = new ArrayList<Group>();
        Button addGroupButton =(Button) findViewById(R.id.button_add_group);
        addGroupButton.setOnClickListener(this);

        Button syncGroup = (Button) findViewById(R.id.button_syc);
        syncGroup.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_group:
                Intent intent = new Intent(getApplicationContext(),AddGroupActivity.class);
                startActivityForResult(intent,REQUEST_ADD_GROUP);
                break;
            case R.id.button_syc:
                PushDataAsync asyncTask = new PushDataAsync();
                asyncTask.execute();
                break;
        }
    }

    public class PushDataAsync extends AsyncTask<String, String, String> {

        byte[] responseString;
        String requestString;

        @Override
        protected String doInBackground(String... strings) {
            JSONObject groupJSONObject = SyncHelper.getJsonGroup(groups.get(0));
            HttpClient httpclient =new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://planindiatest.webatu.com/default.php");
            responseString = new byte[100];
            try {
                ArrayList<NameValuePair> nameValuePairs = SyncHelper.getNameValuePairs(groups.get(0));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                if(response!=null){
                    InputStream in = response.getEntity().getContent();
                    responseString = new byte[50];
                    in.read(responseString);
                }


            } catch (Exception e) {
                String exc = e.getMessage();

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return responseString.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), requestString,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), new String(responseString),Toast.LENGTH_LONG).show();
        }
    }
}