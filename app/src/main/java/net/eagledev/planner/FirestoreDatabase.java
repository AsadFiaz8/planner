package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    CollectionReference routines;
    Formatter f = new Formatter();


    FirestoreDatabase() {
        db = FirebaseFirestore.getInstance();
            setup();
    }

    public void AddAction(Action action){
        if(MainActivity.currentUser != null) {
            if(actions==null){
                setup();
            }
            try{
                actions.document(String.valueOf(action.getId())).set(action);

            } catch(Exception e){
                Log.e(TAG, "AddAction"+e.getMessage());
            }

        }
    }

    public void AddActions(List<Action> actionsList){
        if(MainActivity.currentUser != null){
            if(actions == null){
                setup();
            }
            try{
                for (int i = 0; i < actionsList.size(); i++){
                    actions.document(String.valueOf(actionsList.get(i).getId())).set(actionsList.get(i));
                }
            } catch(Exception e){
                Log.e(TAG, "AddActions"+e.getMessage());
            }

        }
    }

    public void DownloadActions(){
        if(MainActivity.currentUser != null){
            if(actions == null){
                setup();
            }
            actions.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        Calendar start = Calendar.getInstance();
                                        Calendar stop = Calendar.getInstance();
                                        Action action;
                                        Map<String, Object> map = document.getData();
                                        Map<String, Object> startMap = (Map<String, Object>) map.get("start");

                                        start.setTimeInMillis((Long) startMap.get("timeInMillis"));
                                        Map<String, Object> stopMap = (Map<String, Object>) map.get("stop");
                                        stop.setTimeInMillis((Long) stopMap.get("timeInMillis"));

                                        long lcolor = (long) map.get("color");
                                        long licon = (long) map.get("icon");
                                        String desc = (String) map.get("desc");
                                        int id = Integer.parseInt(document.getId());
                                        int color = (int) lcolor;
                                        int icon = (int) licon;

                                        action = new Action(id, desc, start, stop, (int) icon, color);

                                        Log.e(TAG, "Action  " + desc);
                                        if (MainActivity.appDatabase.appDao().idAction(id) != null) {
                                            //Akcja istnieje
                                            MainActivity.appDatabase.appDao().updateAction(action);
                                        } else {
                                            //Akcja nie istnieje
                                            MainActivity.appDatabase.appDao().addAction(action);
                                        }
                                    } catch (Exception e){
                                        Log.e(TAG,"Actions  "+  e.getMessage());
                                    }
                                }
                            }
                            MainActivity.needRefresh = true;
                        }
                    });
        }



    }

    public void AddRoutine(Routine routine){
        if(MainActivity.currentUser != null) {
            if(routines==null){
                setup();
            }
            try{
                routines.document(String.valueOf(routine.getId())).set(routine);

            } catch (Exception e) {
                Log.e(TAG, "AddRoutine "+e.getMessage());
            }

        }
    }

    public void AddRoutines(List<Routine> routinesList){
        if(MainActivity.currentUser != null){
            if(routines == null){
                setup();
            }
            try{
                for (int i = 0; i < routinesList.size(); i++){
                    routines.document(String.valueOf(routinesList.get(i).getId())).set(routinesList.get(i));
                }
            } catch(Exception e){
                Log.e(TAG, "AddRoutines"+e.getMessage());
            }

        }
    }

    public void DownloadRoutines(){
        if(MainActivity.currentUser != null){
            if(routines == null){
                setup();
            }
            routines.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        Calendar start = Calendar.getInstance();
                                        Calendar stop = Calendar.getInstance();
                                        Routine routine;
                                        Map<String, Object> map = document.getData();
                                        Map<String, Object> startMap = (Map<String, Object>) map.get("start");
                                        start.setTimeInMillis((Long) startMap.get("timeInMillis"));
                                        Map<String, Object> stopMap = (Map<String, Object>) map.get("stop");
                                        stop.setTimeInMillis((Long) stopMap.get("timeInMillis"));
                                        boolean monday = (boolean) map.get("monday");
                                        boolean tuesday = (boolean) map.get("tuesday");
                                        boolean wednesday = (boolean) map.get("wednesday");
                                        boolean thursday = (boolean) map.get("thursday");
                                        boolean friday = (boolean) map.get("friday");
                                        boolean saturday = (boolean) map.get("saturday");
                                        boolean sunday = (boolean) map.get("sunday");

                                        long lcolor = (long) map.get("color");
                                        long licon = (long) map.get("icon");
                                        String name = (String) map.get("name");
                                        int id = Integer.parseInt(document.getId());
                                        int color = (int) lcolor;
                                        int icon = (int) licon;

                                        routine = new Routine(id, name, icon, color, start, stop, monday, tuesday, wednesday, thursday, friday, saturday, sunday);

                                        Log.e(TAG, "Routine  " + name);
                                        if (MainActivity.appDatabase.appDao().idRoutine(id) != null) {
                                            //Akcja istnieje
                                            MainActivity.appDatabase.appDao().updateRoutine(routine);
                                        } else {
                                            //Akcja nie istnieje
                                            MainActivity.appDatabase.appDao().addRoutine(routine);
                                        }
                                    } catch (Exception e){
                                        Log.e(TAG,"Routines  "+ e.getMessage());
                                    }
                                }
                            }
                            MainActivity.needRefresh = true;
                        }
                    });
        }



    }

    public void NukeData(){

        deleteActions();
        deleteRoutines();
    }

    public void DeleteAction(int id){
        String sid = String.valueOf(id);
        if(MainActivity.currentUser != null) {
            if (actions == null) {
                setup();
            }
            try {
                actions.document(sid).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e(TAG, "Document action deleted");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting action document", e);
                            }
                        });
            } catch (Exception e){
                Log.e(TAG, "DeleteAction()   "+e.getMessage());
            }
        }
    }

    public void DeleteRoutine(int id){
        String sid = String.valueOf(id);
        if(MainActivity.currentUser != null) {
            if (routines == null) {
                setup();
            }
            try {
                routines.document(sid).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e(TAG, "Document routine deleted");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting routine document", e);
                            }
                        });
            } catch (Exception e){
                Log.e(TAG, "DeleteRoutine()   "+e.getMessage());
            }
        }
    }


    private void deleteActions(){
        if(MainActivity.currentUser != null) {
            if (actions == null) {
                setup();
            }
            actions.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        String id = document.getId();
                                        actions.document(id)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.e(TAG, "Document action deleted");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting action document", e);
                                                    }
                                                });
                                    } catch (Exception e){
                                        Log.e(TAG,"DeletingActions  "+  e.getMessage());
                                    }
                                }
                            }
                            MainActivity.needRefresh = true;
                        }
                    });


        }
    }

    private void deleteRoutines(){
        if(MainActivity.currentUser != null) {
            if (routines == null) {
                setup();
            }
            routines.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        String id = document.getId();
                                        routines.document(id)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.e(TAG, "Document routine deleted");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting routine", e);
                                                    }
                                                });
                                    } catch (Exception e){
                                        Log.e(TAG,"DeletingActions  "+  e.getMessage());
                                    }
                                }
                            }
                            MainActivity.needRefresh = true;
                        }
                    });


        }
    }

    public void setup(){
        if(MainActivity.currentUser != null){
            String mail = MainActivity.currentUser.getEmail();
            String userID = MainActivity.currentUser.getUid();
            users = db.collection("users");
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", userID);
            users.document(mail).set(userMap);
            user = users.document(mail);
            actions = user.collection("actions");
            routines = user.collection("routines");
        }

    }


}
