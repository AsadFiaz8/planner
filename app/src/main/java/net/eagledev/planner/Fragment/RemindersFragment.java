package net.eagledev.planner.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import net.eagledev.planner.Action;
import net.eagledev.planner.Activity.AddReminder;
import net.eagledev.planner.Adapter.ReminderAdapter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Reminder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemindersFragment extends Fragment {

    RecyclerView recyclerView;
    ReminderAdapter adapter;
    List<Reminder> list = new ArrayList<>();
    View view;
    public Context context = getContext();
    ItemClickListener itemClickListener;
    boolean buttonEnabled = false;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int selectedID;
    int duration = 200;
    float pos;
    ImageButton doneButton;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemindersFragment.
     */
    public static RemindersFragment newInstance(String param1, String param2) {
        RemindersFragment fragment = new RemindersFragment();
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

        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        this.view = view;


        doneButton = view.findViewById(R.id.reminder_done);
        doneButton.setVisibility(View.INVISIBLE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reminder reminder = MainActivity.appDatabase.appDao().idReminder(selectedID);
                reminder.setDone(1);
                MainActivity.appDatabase.appDao().updateReminder(reminder);
                setupList();


                doneButton.setVisibility(View.INVISIBLE);
                buttonEnabled = false;
            }
        });

        ImageButton addReminder = view.findViewById(R.id.reminder_add);
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddReminder.class);
                startActivityForResult(intent, 1);
            }
        });




        setupList();

        return view;
    }

    private void setupList() {
        List<Reminder> reminderNoSortList = MainActivity.appDatabase.appDao().getUndoneRminders();
        List <Integer> startTimes = new ArrayList<>();
        for(int s = 0; s<reminderNoSortList.size(); s++){
            startTimes.add(reminderNoSortList.get(s).getStartMinutes());
        }
        Collections.sort(startTimes);
        List<Reminder> act = new ArrayList<>();
        for (int s = 0;  s < reminderNoSortList.size(); s++){
            for(int l = 0; l<reminderNoSortList.size(); l++){
                if(startTimes.get(s) == reminderNoSortList.get(l).getStartMinutes()){
                    boolean isAdded = false;
                    for (Reminder r: act) {
                        if (r.getId() == reminderNoSortList.get(l).getId()){
                            isAdded = true;
                        }
                    }
                    if (!isAdded){
                        act.add(reminderNoSortList.get(l));

                    }
                }
            }

        }


        list = MainActivity.appDatabase.appDao().getUndoneRminders();
        recyclerView = (RecyclerView) view.findViewById(R.id.reminders_reycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reminder reminder = list.get(position);
                selectedID = reminder.getId();
                if(!buttonEnabled) {
                    buttonEnabled = true;

                    doneButton.setVisibility(View.VISIBLE);
                }
            }
        };
        adapter = new ReminderAdapter(context, act, itemClickListener);
        recyclerView.setAdapter(adapter);
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

    /**
     * A simple {@link android.support.v4.app.Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link OnFragmentInteractionListener} interface
     * to handle interaction events.
     * Use the {@link ContactFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MainActivity.CODE_CREATED){
            setupList();
        }
    }
}
