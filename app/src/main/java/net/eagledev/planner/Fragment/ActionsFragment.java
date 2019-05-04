package net.eagledev.planner.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import net.eagledev.planner.Action;
import net.eagledev.planner.Adapter.ActionAdapter;
import net.eagledev.planner.Activity.EditActionActivity;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment {

    Calendar date = Calendar.getInstance();
    Button btnDate;
    //View v;
    DatePickerDialog dpd;
    int year;
    int month;
    int day;
    Formatter formatter = new Formatter();

    Drawable drawable;

    RecyclerView recyclerView;
    ActionAdapter adapter;
    List<Action> actionList = new ArrayList<>();
    View view;
    Context context = getContext();

    ItemClickListener itemClickListener;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ActionsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
        btnDate = view.findViewById(R.id.btn_actions_date);
        this.view = view;
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);

        SetupList();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                int nYear = now.get(Calendar.YEAR);
                int nMonth = now.get(Calendar.MONTH);
                int nDay = now.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int mYear, final int mMonth, final int mDay) {

                        date.set(Calendar.YEAR, mYear);
                        date.set(Calendar.MONTH, mMonth);
                        date.set(Calendar.DAY_OF_MONTH, mDay);
                        btnDate.setText(formatter.Date(date));
                        year = mYear;
                        month = mMonth;
                        day = mDay;

                        LoadActions();
                    }
                },nYear, nMonth , nDay);
                dpd.show();
            }
        });
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private void LoadActions() {
        actionList = MainActivity.appDatabase.appDao().getActionsFromDay(day,month,year);
        adapter = new ActionAdapter(context, actionList ,itemClickListener );
        recyclerView.setAdapter(adapter);
    }

    public void SetupList() {
        actionList = MainActivity.appDatabase.appDao().getActions();
        recyclerView = (RecyclerView) view.findViewById(R.id.actions_reycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Action action = actionList.get(position);
                Intent intentEdit = new Intent(context, EditActionActivity.class);
                intentEdit.putExtra("ID", action.getID());
                startActivityForResult(intentEdit, 1);
            }
        };
        adapter = new ActionAdapter(context, actionList ,itemClickListener );
        recyclerView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (dataIntent != null) {
            String messageReturn = dataIntent.getStringExtra("message_return");
            if(messageReturn.equals("refresh")) {
                SetupList();
            }
        }


    }


}
