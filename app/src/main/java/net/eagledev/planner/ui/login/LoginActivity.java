package net.eagledev.planner.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 5646;
    List<AuthUI.IdpConfig> providers;




   public static final String TAG = "LoginActivity";
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    String email;
    String pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        if(MainActivity.currentUser == null){
            showSignInOptions();
        }

          loginButton = findViewById(R.id.login);
       loginButton.setOnClickListener(new View.OnClickListener() {
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
                               showSignInOptions();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                   }
               });
           }
       });


    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AuthTheme)
                .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
                MainActivity.currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if(MainActivity.currentUser != null){
                    Log.e(TAG, "Current User Email: : "+ MainActivity.currentUser.getEmail());
                    MainActivity.fDatabase.downloadActions();
                    MainActivity.fDatabase.downloadRoutines();
                    MainActivity.needRefresh = true;
                }



        }
    }

    private void updateUI(FirebaseUser user) {
    }


}
