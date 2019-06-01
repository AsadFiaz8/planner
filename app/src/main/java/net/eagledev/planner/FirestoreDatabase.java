package net.eagledev.planner;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreDatabase {

    FirebaseFirestore db;
    CollectionReference users;
    DocumentReference user;
    CollectionReference actions;


    FirestoreDatabase() {
        db = FirebaseFirestore.getInstance();
        if(MainActivity.currentUser != null){
            users = db.collection("users");
            Map<String, Object> userMap = new HashMap<>();
            String mail = MainActivity.currentUser.getEmail();
            String userID = MainActivity.currentUser.getUid();
            userMap.put("id", userID);
            users.document(mail).set(userMap);
            user = users.document(mail);
            actions = user.collection("tables");
            //dokument to akcja


        }

    }

}
