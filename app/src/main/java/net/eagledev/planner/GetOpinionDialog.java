package net.eagledev.planner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GetOpinionDialog implements View.OnClickListener {

    public static final String TAG = "GetOpinionDialog";
    Context context;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    Button buttonYes;
    Button buttonNo;
    Button buttonSend;
    EditText userText;
    Dialog d;
    LinearLayout layoutPlayStore;
    LinearLayout layoutChanges;
    FirebaseFirestore db;
    EditText opinionUserText;

    public GetOpinionDialog(Context context){
        this.context = context;
    }

    public void ShowDialog(){
        d = new Dialog(context);
        d.setContentView(R.layout.dialog_get_opinion);
        d.show();

        db = FirebaseFirestore.getInstance();
        star1 = d.findViewById(R.id.opinion_star1);
        star1.setOnClickListener(this);
        star2 = d.findViewById(R.id.opinion_star2);
        star2.setOnClickListener(this);
        star3 = d.findViewById(R.id.opinion_star3);
        star3.setOnClickListener(this);
        star4 = d.findViewById(R.id.opinion_star4);
        star4.setOnClickListener(this);
        star5 = d.findViewById(R.id.opinion_star5);
        star5.setOnClickListener(this);

        buttonYes = d.findViewById(R.id.opinion_button_yes);
        buttonYes.setOnClickListener(this);
        buttonNo = d.findViewById(R.id.opinion_button_no);
        buttonNo.setOnClickListener(this);
        buttonSend = d.findViewById(R.id.opinion_button_send);
        buttonSend.setOnClickListener(this);
        userText = d.findViewById(R.id.opinion_user_text);
        layoutPlayStore = d.findViewById(R.id.opinion_layout_play_store);
        layoutChanges = d.findViewById(R.id.opinion_layout_changes);
        opinionUserText = d.findViewById(R.id.opinion_user_text);
    }

    private void setStars(int star){
        switch (star){
            case 1:
                star1.setImageDrawable(context.getDrawable(R.drawable.star2));
                star2.setImageDrawable(context.getDrawable(R.drawable.star));
                star3.setImageDrawable(context.getDrawable(R.drawable.star));
                star4.setImageDrawable(context.getDrawable(R.drawable.star));
                star5.setImageDrawable(context.getDrawable(R.drawable.star));
                layoutPlayStore.setVisibility(View.INVISIBLE);
                layoutChanges.setVisibility(View.VISIBLE);
                break;
            case 2:
                star1.setImageDrawable(context.getDrawable(R.drawable.star2));
                star2.setImageDrawable(context.getDrawable(R.drawable.star2));
                star3.setImageDrawable(context.getDrawable(R.drawable.star));
                star4.setImageDrawable(context.getDrawable(R.drawable.star));
                star5.setImageDrawable(context.getDrawable(R.drawable.star));
                layoutPlayStore.setVisibility(View.INVISIBLE);
                layoutChanges.setVisibility(View.VISIBLE);
                break;
            case 3:
                star1.setImageDrawable(context.getDrawable(R.drawable.star2));
                star2.setImageDrawable(context.getDrawable(R.drawable.star2));
                star3.setImageDrawable(context.getDrawable(R.drawable.star2));
                star4.setImageDrawable(context.getDrawable(R.drawable.star));
                star5.setImageDrawable(context.getDrawable(R.drawable.star));
                layoutPlayStore.setVisibility(View.INVISIBLE);
                layoutChanges.setVisibility(View.VISIBLE);
                break;
            case 4:
                star1.setImageDrawable(context.getDrawable(R.drawable.star2));
                star2.setImageDrawable(context.getDrawable(R.drawable.star2));
                star3.setImageDrawable(context.getDrawable(R.drawable.star2));
                star4.setImageDrawable(context.getDrawable(R.drawable.star2));
                star5.setImageDrawable(context.getDrawable(R.drawable.star));
                layoutPlayStore.setVisibility(View.INVISIBLE);
                layoutChanges.setVisibility(View.VISIBLE);
                break;
            case 5:
                star1.setImageDrawable(context.getDrawable(R.drawable.star2));
                star2.setImageDrawable(context.getDrawable(R.drawable.star2));
                star3.setImageDrawable(context.getDrawable(R.drawable.star2));
                star4.setImageDrawable(context.getDrawable(R.drawable.star2));
                star5.setImageDrawable(context.getDrawable(R.drawable.star2));
                layoutPlayStore.setVisibility(View.VISIBLE);
                layoutChanges.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opinion_button_no:
                MainActivity.valueHolder.setGaveOpinion(true);
                d.dismiss();
                break;
            case R.id.opinion_button_yes:
                MainActivity.valueHolder.setGaveOpinion(true);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=net.degeapps.planner"));
                intent.setPackage("com.android.vending");
                context.startActivity(intent);
                break;
            case R.id.opinion_button_send:
                String opinion = String.valueOf(opinionUserText.getText());
                Map<String, Object> message = new HashMap<>();
                message.put("opinion", opinion);

                db.collection("opinions")
                        .add(message)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                MainActivity.needShowMainPage = true;

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);

                            }
                        });
                MainActivity.valueHolder.setGaveOpinion(true);
                d.dismiss();
                break;
            case R.id.opinion_star1:
                setStars(1);
                break;
            case R.id.opinion_star2:
                setStars(2);
                break;
            case R.id.opinion_star3:
                setStars(3);
                break;
            case R.id.opinion_star4:
                setStars(4);
                break;
            case R.id.opinion_star5:
                setStars(5);
                break;
        }
    }
}
