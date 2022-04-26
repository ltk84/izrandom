package uit.itszoo.izrandom.random_module.random_integer.random_integer_custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;
import uit.itszoo.izrandom.random_module.random_integer.RandomIntegerActivity;

public class RandomIntegerCustomActivity extends AppCompatActivity {
    public static final String NEW_CUS_NUM = "NEW_CUS_NUM";
    ArrayList<Integer> listCusNum;

    ImageButton backButton;
    ImageButton clearButton;
    ImageButton confirmButton;
    EditText editTextListNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_integer_custom);

        listCusNum = getIntent().getIntegerArrayListExtra(RandomIntegerActivity.CURRENT_CUS_NUM);

        initView();
    }

    private void initView() {
        backButton = findViewById(R.id.bb_rand_integer_cus);
        clearButton = findViewById(R.id.ran_num_cus_clean);
        confirmButton = findViewById(R.id.ran_num_custom_selected);
        editTextListNum = findViewById(R.id.list);

        editTextListNum.setText(listCusNum.toString().substring(1, listCusNum.toString().length() -1));

//        editTextListNum.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.)) {
//                    // Perform action on key press
//                    handleEditTextEnterEvent();
//                    return true;
//                }
//                return false;
//            }
//        });

        editTextListNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    handleEditTextEnterEvent();
                }
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextListNum.setText("");
                listCusNum.clear();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditTextEnterEvent();

                Intent intentBack = new Intent();
                intentBack.putExtra(RandomIntegerCustomActivity.NEW_CUS_NUM, listCusNum);
                setResult(Activity.RESULT_OK, intentBack);
                finish();

            }
        });

    }

    private void handleEditTextEnterEvent() {
        String inputString = editTextListNum.getText().toString();
        inputString = inputString.replaceAll("\\s+","");
        String[] listString = (inputString.split(","));

        ArrayList<Integer> listNum = new ArrayList<Integer>();

        for (int i = 0; i < listString.length; i++) {
            if (listString[i] == null || listString[i].equals("")) continue;
            else {
                try {
                    Integer value = Integer.parseInt(listString[i]);
                    if (!listNum.contains(value)) listNum.add(value);
                }
                catch (Exception e) {
                    continue;
                }
            }
            Collections.sort(listNum);
        }

        listCusNum = listNum;

        editTextListNum.setText(listCusNum.toString().substring(1, listCusNum.toString().length() -1));
        editTextListNum.setSelection(editTextListNum.getText().toString().length());
    }
}
