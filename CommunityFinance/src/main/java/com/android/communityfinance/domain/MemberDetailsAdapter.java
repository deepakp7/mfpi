package com.android.communityfinance.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by shashank on 2/3/14.
 */
public class MemberDetailsAdapter extends BaseAdapter {

    private Context context;
    public Member[] members;

    public MemberDetailsAdapter(Context context,Member[] members)
    {
        this.context=context;
        this.members = members;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;



        return null;
    }
}
