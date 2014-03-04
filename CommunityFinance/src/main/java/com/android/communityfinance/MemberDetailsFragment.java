package com.android.communityfinance;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.communityfinance.domain.Member;

public class MemberDetailsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "FirstName";
    private static final String ARG_PARAM2 = "LastName";
    private static final String ARG_PARAM3 = "ContactInfo";
    public static final int RESULT_SAVED = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    DatabaseHandler dbHandler;
    ViewHelper viewHelper;
    Member member;
    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberDetailsFragment newInstance(String param1, String param2,String param3) {
        MemberDetailsFragment fragment = new MemberDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public MemberDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new DatabaseHandler(getActivity());
        viewHelper = new ViewHelper();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);

            member = new Member();
            member.FirstName=mParam1;
            member.LastName=mParam2;
            member.ContactInfo=mParam3;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_member_details, container,false);

        if (view != null) {
            Button saveButton = (Button) view.findViewById(R.id.button_save_member);
            saveButton.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        try{
            switch(view.getId())
            {
                case R.id.button_save_member:
                    Member updatedMember = ViewHelper.fetchMemberDetailsFromView(getActivity().findViewById(R.id.layout_member_details_container));
                    Toast.makeText(getActivity(),"Calling save",Toast.LENGTH_SHORT).show();
                    dbHandler.addUpdateMember(updatedMember);
                    FragmentActivity fragActivity = getActivity();
                    // If both fragments are in same activity, reload activity
                    // else return result as saved
                    if(fragActivity instanceof MemberScreenActivity)
                    {
                        ((MemberScreenActivity) fragActivity).Reload();
                    }
                    else
                    {
                        Intent returnIntent = new Intent();
                        fragActivity.setResult(RESULT_SAVED, returnIntent);
                        fragActivity.finish();
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }

    }





    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
