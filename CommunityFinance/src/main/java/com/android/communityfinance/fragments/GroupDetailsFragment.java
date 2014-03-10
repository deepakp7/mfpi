package com.android.communityfinance.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.communityfinance.R;
import com.android.communityfinance.ViewHelper;
import com.android.communityfinance.database.DatabaseHandler;
import com.android.communityfinance.domain.Group;


public class GroupDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "groupUID";

    private int groupUID;
    private Group group;
    DatabaseHandler db_handler;

    // Use this static factory method to instantiate
    public static GroupDetailsFragment newInstance(int groupId) {
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, groupId);
        fragment.setArguments(args);
        return fragment;
    }
    public GroupDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupUID = getArguments().getInt(ARG_PARAM1);
        }
        db_handler = new DatabaseHandler(getActivity());
        group = db_handler.getGroup(groupUID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Button saveGroupButton = (Button) getActivity().findViewById(R.id.button_save_group_details);
        if(saveGroupButton != null) saveGroupButton.setOnClickListener(this);
        ViewHelper.populateGroupDetailsToView(getActivity().findViewById(R.id.layout_group_details),group);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_save_group:
                Group group = ViewHelper.fetchGroupDetailsFromView(getActivity().findViewById(R.id.layout_group_details));
                if(group != null)
                {
                    db_handler.addUpdateGroup(group);
                }
                Toast.makeText(getActivity(), "Group Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
