package net.eagledev.planner.Fragment;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageButton;

import net.eagledev.planner.Adapter.AimAdapter;
import net.eagledev.planner.Aim;
import net.eagledev.planner.Activity.EditAimActivity;
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
 * {@link AimsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AimsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AimsFragment extends Fragment {


    Calendar date = Calendar.getInstance();
    Button btnFilter;
    Dialog dialog;
    Formatter f = new Formatter();
    RecyclerView recyclerView;
    AimAdapter adapter;
    List<Aim> aimList = new ArrayList<>();
    View view;
    Context context = getContext();
    ItemClickListener itemClickListener;
    ItemClickListener mainListener;

    private OnFragmentInteractionListener mListener;

    public AimsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AimsFragment.
     */

    public static AimsFragment newInstance(String param1, String param2) {
        AimsFragment fragment = new AimsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_aims, container, false);
        btnFilter = view.findViewById(R.id.btn_aims_filter);
        setupList();
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO dialog z wyborem filtrów
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
       /* if (context instanceof OnFragmentInteractionListener) {
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

    private void setupList(){
        aimList = MainActivity.appDatabase.appDao().getAims();
        recyclerView = (RecyclerView) view.findViewById(R.id.aims_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Aim aim = aimList.get(position);
                aim.setCompleted(!aim.isCompleted());
                ImageButton imageButton = view.findViewById(R.id.aim_list_button);
                if(aim.isCompleted()) {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ui21));
                }else {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ui96));
                }
            }
        };
        mainListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Aim aim = aimList.get(position);
                Intent intentEdit = new Intent(context, EditAimActivity.class);
                intentEdit.putExtra("ID", aim.getId());
                startActivityForResult(intentEdit, 1);
            }
        };
        adapter = new AimAdapter(context, aimList, itemClickListener, mainListener, true);
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
