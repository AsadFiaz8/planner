package net.eagledev.planner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {


    Context context;
    Switch switch1;
    Switch switch2;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActionsFragment.
     */
    public static ActionsFragment newInstance(String param1, String param2) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //v = inflater.inflate(R.layout.fragment_actions, container, false);

        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        switch1 = view.findViewById(R.id.settings_switch1);
        switch1.setChecked(!MainActivity.valueHolder.isDatePickerButton());
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch1.isChecked()) {
                    MainActivity.valueHolder.setDatePickerButton(false);
                } else MainActivity.valueHolder.setDatePickerButton(true);
            }
        });
        switch2 = view.findViewById(R.id.settings_switch2);
        switch2.setChecked(MainActivity.valueHolder.isMainNotification());
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch2.isChecked()) {
                    MainActivity.valueHolder.setMainNotification(true);
                } else MainActivity.valueHolder.setMainNotification(false);
            }
        });
        view.findViewById(R.id.settings_button1).setOnClickListener(this);
        view.findViewById(R.id.settings_button2).setOnClickListener(this);

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_button1:
                Uri uriUrl = Uri.parse("https://degeapps.wixsite.com/mojawitryna/privacy-policy");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
                break;
            case R.id.settings_button2:
                MainActivity.appDatabase.appDao().nukeActionsTable();
                MainActivity.appDatabase.appDao().nukeAimsTable();
                MainActivity.appDatabase.appDao().nukeRemindersTable();
                MainActivity.appDatabase.appDao().nukeRoutinesTable();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 365);
                //MainActivity.fDatabase.setPremiumTime(calendar.getTimeInMillis());
                break;
        }
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
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        String messageReturn = dataIntent.getStringExtra("message_return");
        if(messageReturn.equals("refresh")) {
        }
    }


}
