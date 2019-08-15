package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FirestoreDatabase {

    public static final String TAG = "FirestoreDatabase";
    FirebaseFirestore db;
    CollectionReference users;
    DocumentReference user;
    CollectionReference actions;
    CollectionReference routines;
    CollectionReference info;
    Formatter f = new Formatter();


    FirestoreDatabase() {
        db = FirebaseFirestore.getInstance();
            setup();
    }

    public void setPremium(boolean premium){
        try{
            if(MainActivity.currentUser != null) {
                if (info == null) {
                    setup();
                }

                Map<String, Object> map = new HashMap<>();
                map.put("premium", premium);
                info.document("premium").set(map);
            }
        } catch(Exception e){
            Log.e(TAG, "setPremium "+e.getMessage());
        }
    }

    public void setPremiumTime(long premiumTime){
        try{
            if(MainActivity.currentUser != null) {
                if (user == null) {
                    setup();
                }
                Map<String, Object> map = new HashMap<>();
                map.put("premiumTime", premiumTime);
                user.set(map);
            }
        } catch(Exception e){
            Log.e(TAG, "setPremium "+e.getMessage());
        }
    }

    public long getPremiumTime(){
        try{
            final long premiumTime = 0;
            if(MainActivity.currentUser != null) {
                if (user == null) {
                    setup();
                }
                user.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    try {
                                        DocumentSnapshot documentSnapshot=  task.getResult();
                                        Map<String, Object> map = documentSnapshot.getData();
                                        long premiumTime = (long)map.get("premiumTime");
                                        MainActivity.valueHolder.setPremiumTime(premiumTime);
                                    }catch (Exception e){
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            }
                        });
            }

        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return MainActivity.valueHolder.premiumTime();
    }

    public boolean getPremium() {
        try {
            if (MainActivity.currentUser != null) {
                if (user == null) {
                    setup();
                }
                info.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                    }
                                    }

                            }
                        });

                        /*
                        {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    try {
                                        DocumentSnapshot documentSnapshot=  task.getResult();
                                        Map<String, Object> map = documentSnapshot.getData();
                                        MainActivity.valueHolder.setPremiumUser((boolean)map.get("premium"));
                                    }catch (Exception e){
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            }
                        });
                        */

            }
            }catch(Exception e){
                Log.e(TAG, e.getMessage());
            }
            return MainActivity.valueHolder.getPremiumUser();
    }



    public void AddAction(Action action){
        try{
        if(MainActivity.currentUser != null) {
            if (actions == null) {
                setup();
            }
            actions.document(String.valueOf(action.getId())).set(action);
        }
            } catch(Exception e){
                Log.e(TAG, "AddAction "+e.getMessage());
            }
    }

    public void AddActions(List<Action> actionsList){
        try{
        if(MainActivity.currentUser != null) {
            if (actions == null) {
                setup();
            }
            for (int i = 0; i < actionsList.size(); i++){
                actions.document(String.valueOf(actionsList.get(i).getId())).set(actionsList.get(i));
            }
        }

            } catch(Exception e){
                Log.e(TAG, "AddActions"+e.getMessage());
            }
    }

    public void DownloadActions(){
        try {
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
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }




    }

    public void AddRoutine(Routine routine){
        try{
        if(MainActivity.currentUser != null) {
            if (routines == null) {
                setup();
            }

            routines.document(String.valueOf(routine.getId())).set(routine);
        }
            } catch (Exception e) {
                Log.e(TAG, "AddRoutine "+e.getMessage());
            }
    }

    public void AddRoutines(List<Routine> routinesList){
        try{
        if(MainActivity.currentUser != null) {
            if (routines == null) {
                setup();
            }

            for (int i = 0; i < routinesList.size(); i++) {
                routines.document(String.valueOf(routinesList.get(i).getId())).set(routinesList.get(i));
            }
        }
            } catch(Exception e){
                Log.e(TAG, "AddRoutines"+e.getMessage());
            }
    }

    public void DownloadRoutines(){
        try {
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
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }




    }

    public void NukeData(){

        deleteActions();
        deleteRoutines();
    }

    public void DeleteAction(int id){
        String sid = String.valueOf(id);
        try {
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
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public void DeleteRoutine(int id){
        String sid = String.valueOf(id);
        try {
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
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }


    private void deleteActions(){
        try {
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
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void deleteRoutines(){
        try {
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
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public void setup(){
        try {
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
                info = user.collection("info");

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


    }


}
