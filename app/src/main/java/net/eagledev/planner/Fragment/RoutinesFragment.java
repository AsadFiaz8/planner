package net.eagledev.planner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.eagledev.planner.Adapter.RoutineAdapter;
import net.eagledev.planner.Activity.EditRoutineActivity;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Routine;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoutinesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoutinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutinesFragment extends Fragment {

    Formatter f = new Formatter();
    RecyclerView recyclerView;
    List<Routine> routinesList = new ArrayList<>();
    View view;
    Context context = getContext();
    ItemClickListener itemClickListener;
    RoutineAdapter adapter;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RoutinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoutinesFragment.
     */

    public static RoutinesFragment newInstance(String param1, String param2) {
        RoutinesFragment fragment = new RoutinesFragment();
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
        view = inflater.inflate(R.layout.fragment_routines, container, false);
        context = getActivity();
        setupList();
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

    private void setupList() {
        routinesList = MainActivity.appDatabase.appDao().getRoutines();
        recyclerView = (RecyclerView) view.findViewById(R.id.routines_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Routine routine = routinesList.get(position);
                Intent intentEdit = new Intent(context, EditRoutineActivity.class);
                intentEdit.putExtra("ID", routine.getId());
                startActivityForResult(intentEdit, 1);
            }
        };

        adapter = new RoutineAdapter(context, routinesList, itemClickListener);
        recyclerView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if(dataIntent != null) {
            String messageReturn = dataIntent.getStringExtra("message_return");
            if(messageReturn.equals("refresh")) {
                setupList();
            }
        }

    }
}
