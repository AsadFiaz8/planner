package net.eagledev.planner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {

    ImageView toolbar_confirm;
    ImageView toolbar_cancel;
    TextView nameText;
    Button dateButton;
    Spinner repeeatSpinner;
    EditText gapTerxt;
    Spinner timeSpinner;
    Spinner labelSpinner;
    ImageButton priorityButton1;
    ImageButton priorityButton2;
    ImageButton priorityButton3;
    ImageButton priorityButton4;
    EditText commnentText;
    ImageButton dayButton1;
    ImageButton dayButton2;
    ImageButton dayButton3;
    ImageButton dayButton4;
    ImageButton dayButton5;
    ImageButton dayButton6;
    ImageButton dayButton7;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setup();

    }

    private void setup() {

    }
}
