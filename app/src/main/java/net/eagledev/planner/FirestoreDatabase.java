package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FirestoreDatabase {

    public static final String TAG = "FirestoreDatabase";
    FirebaseFirestore db;
    CollectionReference users;
    DocumentReference user;
    CollectionReference actions;
    Formatter f = new Formatter();


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
            /*
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
            actions.document("0").set(actionMap);*/
        }
    }

    public void addAction(Action action){
        if(MainActivity.currentUser != null) {
            actions.document(String.valueOf(action.getId())).set(action);

        }
    }

    public void addActions(List<Action> actionsList){
        if(MainActivity.currentUser != null){
            for (int i = 0; i < actionsList.size(); i++){
                actions.document(String.valueOf(actionsList.get(i).getId())).set(actionsList.get(i));
            }
        }
    }

    public void downloadActions(){
        actions.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                if(MainActivity.appDatabase.appDao().idAction(Integer.parseInt(document.getId())) != null){
                                    Calendar start = Calendar.getInstance();
                                    Map<String, Object> map = document.getData();
                                    Long start_year = (Long) document.get("start_year");
                                    Long start_month = (Long) document.get("start_month");
                                    Long start_day = (Long) document.get("start_day");
                                    Long start_hour = (Long) document.get("start_hour");
                                    Long start_minute = (Long) document.get("start_minute");
                                    start.set(Calendar.YEAR, start_year.intValue());
                                    start.set(Calendar.MONTH, start_month.intValue());
                                    start.set(Calendar.DAY_OF_MONTH, start_day.intValue());
                                    start.set(Calendar.HOUR_OF_DAY, start_hour.intValue());
                                    start.set(Calendar.MINUTE, start_minute.intValue());

                                    Log.e(TAG, "cal  " + f.dateWithTime(start));
                                }

                            }
                        } else {

                        }
                    }
                });
    }


}
