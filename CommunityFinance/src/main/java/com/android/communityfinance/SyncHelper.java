package com.android.communityfinance;

import com.android.communityfinance.domain.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.communityfinance.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {
    public static JSONObject getJsonGroup(Group group)
    {
        JSONObject groupJSON = new JSONObject();
        try {
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_UID,group.UID);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_NAME,group.GroupName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return groupJSON;
    }

    public static ArrayList<NameValuePair> getNameValuePairs(Group group)
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(DatabaseHandler.COLUMN_GROUP_UID,String.valueOf(group.UID)));
        nameValuePairs.add(new BasicNameValuePair(DatabaseHandler.COLUMN_GROUP_NAME,group.GroupName));

        return nameValuePairs;
    }

}