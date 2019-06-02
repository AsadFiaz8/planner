package net.eagledev.planner;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreDatabase {

    public static final String TAG = "FirestoreDatabase";
    FirebaseFirestore db;
    CollectionReference users;
    DocumentReference user;
    CollectionReference actions;


    FirestoreDatabase() {
        db = FirebaseFirestore.getInstance();

        if(MainActivity.currentUser != null){
            String mail = MainActivity.currentUser.getEmail();
            String userID = MainActivity.currentUser.getUid();
            users = db.collection("users");
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", userID);
            users.document(mail).set(userMap);
            user = users.document(mail);

            actions = user.collection("actions");
            Log.e(TAG, actions.getPath());
            //dokument to akcja
            Map<String, Object> actionMap = new HashMap<>();
            actionMap.put("id", 0);
            actionMap.put("desc", "Test");
            actionMap.put("icon", MainActivity.icons[1]);
            actionMap.put("color", MainActivity.colors[1]);
            actionMap.put("start_year",0);
            actionMap.put("start_month",0);
            actionMap.put("start_day",0);
            actionMap.put("start_hour",0);
            actionMap.put("start_minute",0);
            actionMap.put("stop_year",0);
            actionMap.put("stop_month",0);
            actionMap.put("stop_day",0);
            actionMap.put("stop_hour",0);
            actionMap.put("stop_minute",0);
            actions.document("0").set(actionMap);
        }
    }

    public void addAction(Action action){
        actions.document(String.valueOf(action.getId())).set(action);
    }

}
