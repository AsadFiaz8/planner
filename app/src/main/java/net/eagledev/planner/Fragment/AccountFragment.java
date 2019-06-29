package net.eagledev.planner.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.Arrays;
import java.util.List;

import static net.eagledev.planner.MainActivity.mFirebaseAnalytics;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private static final int MY_REQUEST_CODE = 5646;
    List<AuthUI.IdpConfig> providers;
    TextView userNameText;
    TextView userMailText;
    Button btnNuke;
    Button btnLogout;
    Button btnLogin;
    LinearLayout loginLayout;
    LinearLayout logoutLayout;
    public static final String TAG = "AccountFragment";

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        userNameText = view.findViewById(R.id.user_id);
        userMailText = view.findViewById(R.id.user_mail);
        btnLogout = view.findViewById(R.id.user_btn_logout);
        btnNuke = view.findViewById(R.id.user_btn_nuke_data);
        btnLogin = view.findViewById(R.id.user_btn_login);
        loginLayout = view.findViewById(R.id.user_logout_login);
        logoutLayout = view.findViewById(R.id.user_layout_logout);
        if(MainActivity.currentUser == null){
            showSignInOptions();
        } else {
            logoutLayout.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.VISIBLE);
            userNameText.setText(MainActivity.currentUser.getDisplayName());
            userMailText.setText(MainActivity.currentUser.getEmail());
        }



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(MainActivity.context)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                MainActivity.appDatabase.appDao().nukeActionsTable();
                                MainActivity.appDatabase.appDao().nukeRoutinesTable();
                                MainActivity.appDatabase.appDao().nukeAimsTable();
                                MainActivity.appDatabase.appDao().nukeRemindersTable();
                                MainActivity.needRefresh= true;
                                MainActivity.currentUser = null;
                                loginLayout.setVisibility(View.INVISIBLE);
                                logoutLayout.setVisibility(View.VISIBLE);
                                MainActivity.toolbar.setTitle(R.string.account);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });

        btnNuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fDatabase.NukeData();
                MainActivity.appDatabase.appDao().nukeActionsTable();
                MainActivity.appDatabase.appDao().nukeRoutinesTable();
                MainActivity.appDatabase.appDao().nukeAimsTable();
                MainActivity.appDatabase.appDao().nukeRemindersTable();
                MainActivity.needRefresh= true;
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInOptions();
            }
        });
        return view;
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AuthTheme)
                        .build(), MY_REQUEST_CODE
        );
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            MainActivity.currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if(MainActivity.currentUser != null){
                Log.e(TAG, "Current User Email: : "+ MainActivity.currentUser.getEmail());
                MainActivity.fDatabase.DownloadActions();
                MainActivity.fDatabase.DownloadRoutines();
                logoutLayout.setVisibility(View.INVISIBLE);
                loginLayout.setVisibility(View.VISIBLE);
                userNameText.setText(MainActivity.currentUser.getDisplayName());
                userMailText.setText(MainActivity.currentUser.getEmail());
                MainActivity.toolbar.setTitle(R.string.account);
                MainActivity.needRefresh = true;
                MainActivity.setMainPage = true;
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "Login");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                Toast.makeText(MainActivity.context, getString(R.string.signed_in_by_mail)+"  "+MainActivity.currentUser.getEmail(),Toast.LENGTH_LONG).show();
            }
        }
    }
}
