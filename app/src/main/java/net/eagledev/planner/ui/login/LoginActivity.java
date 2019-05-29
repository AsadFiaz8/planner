package net.eagledev.planner.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import static net.eagledev.planner.MainActivity.mAuth;

public class LoginActivity extends AppCompatActivity {

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
        

          usernameEditText = findViewById(R.id.username);
          passwordEditText = findViewById(R.id.password);
          loginButton = findViewById(R.id.login);
         ProgressBar loadingProgressBar = findViewById(R.id.loading);
       
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 email = String.valueOf(usernameEditText.getText());
                 pass = String.valueOf(passwordEditText.getText());
                 mAuth.createUserWithEmailAndPassword(email, pass)
                         .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     // Sign in success, update UI with the signed-in user's information
                                     Log.d(TAG, "createUserWithEmail:success");
                                     FirebaseUser user = mAuth.getCurrentUser();
                                     updateUI(user);
                                 } else {
                                     // If sign in fails, display a message to the user.
                                     Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                     
                                 }
                             }
                         })
             }
         });
        
    }

    private void updateUI(FirebaseUser user) {
    }


}
