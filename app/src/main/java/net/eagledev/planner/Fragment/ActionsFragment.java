package net.eagledev.planner.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import net.eagledev.planner.Action;
import net.eagledev.planner.Activity.AddActionAtivity;
import net.eagledev.planner.Adapter.ActionAdapter;
import net.eagledev.planner.Activity.EditActionActivity;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.ArrayList;
import java.util.Calendar;
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
public class ActionsFragment extends Fragment {

    Calendar date = Calendar.getInstance();
    Button btnDate;
    //View v;
    DatePickerDialog dpd;
    int year;
    int month;
    int day;
    Dialog dialog;
    Formatter formatter = new Formatter();
    NumberPicker monthPicker;
    NumberPicker yearPicker;

    Drawable drawable;

    RecyclerView recyclerView;
    ActionAdapter adapter;
    List<Action> actionList = new ArrayList<>();
    View view;
    Context context = getContext();

    List<List<Action>> actionLister = new ArrayList<>();
    List<RecyclerView> recyclerList = new ArrayList<>();
    List<ItemClickListener> itemListnerList = new ArrayList<>();



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



        SetupList(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);
                dialog.setTitle("Select Month");
                dialog.setContentView(R.layout.dialog_set_month);

                monthPicker = dialog.findViewById(R.id.month_picker);
                yearPicker = dialog.findViewById(R.id.year_picker);

                String[] displayMonth = {getString(R.string.january), getString(R.string.february), getString(R.string.march), getString(R.string.april), getString(R.string.may), getString(R.string.june), getString(R.string.july), getString(R.string.august), getString(R.string.september), getString(R.string.october), getString(R.string.november), getString(R.string.december)};
                monthPicker.setMaxValue(11);
                monthPicker.setMinValue(0);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(Calendar.getInstance().get(Calendar.MONTH));
                monthPicker.setDisplayedValues(displayMonth);
                monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


                yearPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR)+10);
                yearPicker.setMinValue(Calendar.getInstance().get(Calendar.YEAR)-10);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(Calendar.getInstance().get(Calendar.YEAR));
                yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


                Button selectButton = dialog.findViewById(R.id.btn_select_month);
                selectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetupList(monthPicker.getValue(), yearPicker.getValue());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return view;
    }

    private void setupRecyclerList() {
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view1));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view2));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view3));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view4));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view5));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view6));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view7));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view8));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view9));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view10));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view11));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view12));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view13));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view14));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view15));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view16));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view17));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view18));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view19));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view20));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view21));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view22));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view23));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view24));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view25));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view26));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view27));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view28));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view29));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view30));
        recyclerList.add((RecyclerView) view.findViewById(R.id.actions_reycler_view31));

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

    public void SetupList(int month, int year) {
        setupRecyclerList();
        actionLister.clear();
        Calendar cal = Calendar.getInstance();
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1;i <= max; i++){


            List<Action> actions = MainActivity.appDatabase.appDao().getActionsFromDay(i,month,year);

            Log.e("ActionsFragment", i + " - " +String.valueOf(actions.size()));

            List <Integer> startTimes = new ArrayList<>();
            for(int s = 0; s<actions.size(); s++){
                startTimes.add(actions.get(s).getStartMinutes());
            }
            Collections.sort(startTimes);
            List<Action> act = new ArrayList<>();
            for (int s = 0;  s < actions.size(); s++){
                for(int l = 0; l<actions.size(); l++){
                    if(startTimes.get(s) == actions.get(l).getStartMinutes()){
                        act.add(actions.get(l));
                    }
                }

            }




            actionLister.add(act);




            recyclerList.get(i).setHasFixedSize(true);
            recyclerList.get(i).setLayoutManager(new LinearLayoutManager(context));
            final List<Action> aList = actionLister.get(i-1);
            itemListnerList.add(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Action action = aList.get(position);
                    Intent intentEdit = new Intent(context, AddActionAtivity.class);
                    intentEdit.putExtra("ID", action.getId());
                    intentEdit.putExtra("edit", true);
                    startActivityForResult(intentEdit, 1);
                }
            });
            adapter = new ActionAdapter(context, actionLister.get(i-1) ,itemListnerList.get(i-1));
            recyclerList.get(i-1).setAdapter(adapter);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (dataIntent != null) {
            String messageReturn = dataIntent.getStringExtra("message_return");
            if(messageReturn.equals("refresh")) {
                //SetupList();
            }
        }


    }


}
