package com.android.communityfinance;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.communityfinance.domain.Member;

/**
 * Created by shashank on 4/3/14.
 * Contains UI helper functions to populate and fetch data from UI
 */
public class ViewHelper {

    public static void populateMemberDetailsToView(View view_layout,Member memberToPopulate)
    {
        if(view_layout == null) return;

        View view = view_layout.findViewById(R.id.layout_member_details);

        if(view == null || memberToPopulate == null) return;

        TextView memberIdText = (TextView) view.findViewById(R.id.layout_member_uid);
        if(memberIdText != null) memberIdText.setText(String.valueOf(memberToPopulate.UID));

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null) firstNameEditor.setText(memberToPopulate.FirstName);

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null) lastNameEditor.setText(memberToPopulate.LastName);

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null) contactEditor.setText(memberToPopulate.ContactInfo);
    }

    public static Member fetchMemberDetailsFromView(View viewLayout)
    {
        if(viewLayout == null) return null;

        View view = viewLayout.findViewById(R.id.layout_member_details);

        if(view == null) return null;

        Member updatedMember = new Member();

        TextView memberIdText = (TextView) view.findViewById(R.id.layout_member_uid);
        if(memberIdText != null && memberIdText.getText() != null)
        {
            String uid_string = memberIdText.getText().toString();
            if(uid_string != null && !uid_string.isEmpty())
                updatedMember.UID = Integer.parseInt(uid_string);
        }

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null && firstNameEditor.getText()!= null) updatedMember.FirstName = firstNameEditor.getText().toString();

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null && lastNameEditor.getText()!= null) updatedMember.LastName = lastNameEditor.getText().toString();

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null && contactEditor.getText() != null) updatedMember.ContactInfo = contactEditor.getText().toString();

        return updatedMember;
    }
}
