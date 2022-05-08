package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.CardContentDialog;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.CardItemAdapter;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.FlipCardActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;
import uit.itszoo.izrandom.random_module.random_integer.RandomIntegerActivity;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class FlipCardAddActivity extends AppCompatActivity implements FlipCardAddContract.View, OnCardItemClick {
    public static final String NEW_CARD_NAME = "NEW_CARD_NAME";
    public static final String IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT";
    public static final String EDITED_CARD_POSITION = "EDITED_CARD_POSITION";

    String isAddOrEdit;
    String cardName;
    int editedCardPosition;

    ArrayList<String> listCardContents = new ArrayList<>();
    CardAddItemAdapter cardAddItemAdapter;

    FlipCardAddContract.Presenter presenter;
    ImageButton backButton;
    ImageButton confirmButton;
    ImageButton addCardButton;
    EditText editTextCardName;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card_add);

        isAddOrEdit = getIntent().getExtras().getString(FlipCardMenuActivity.IS_ADD_OR_EDIT);
        cardName = getIntent().getExtras().getString(FlipCardMenuActivity.CARD_NAME_TO_EDIT);

        if (isAddOrEdit.equals("EDIT")) {
            editedCardPosition = getIntent().getExtras().getInt(FlipCardMenuActivity.CARD_POSITION_TO_EDIT);
        }

        initView();
        setListenerForView();

        cardAddItemAdapter = new CardAddItemAdapter(this, FlipCardAddActivity.this, listCardContents, this);
        gridView.setAdapter(cardAddItemAdapter);

        presenter = new FlipCardAddPresenter(getApplicationContext(), this);
        setPresenter(presenter);

    }

    private void initView() {
        backButton = findViewById(R.id.bb_flip_card_add);
        confirmButton = findViewById(R.id.btn_confirm_flip_card_add);
        addCardButton = findViewById(R.id.btn_flip_card_add);
        gridView = findViewById(R.id.flip_card_add_grid_view);
        editTextCardName = findViewById(R.id.edit_text_card_name);

        if (isAddOrEdit.equals("EDIT")) {
            TextView appBarLabelTextView = findViewById(R.id.lb_flip_card_add);
            appBarLabelTextView.setText(R.string.flip_card_edit_label);

            editTextCardName.setText(cardName);

            //TODO get list card contents from database
            for (int i = 0; i < 12; i++) {
                listCardContents.add("What did you want to be when you grew up?");
            }
        }
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listCardContents.size() == 0 && editTextCardName.getText().toString().equals("")) {
                    Intent intentBack = new Intent();
                    setResult(Activity.RESULT_CANCELED, intentBack);
                    finish();
                }
                else {
                    new AlertDialog.Builder(FlipCardAddActivity.this)
                            .setTitle("Hộp thư xác nhận")
                            .setMessage("Bạn có chắc muốn hủy bỏ thay đổi?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intentBack = new Intent();
                                    setResult(Activity.RESULT_CANCELED, intentBack);
                                    finish();
                                }})
                            .setNegativeButton("Hủy", null).show();
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddOrEdit.equals("ADD")) {
                    if (listCardContents.size() == 0 && editTextCardName.getText().toString().equals("")) {
                        Intent intentBack = new Intent();
                        setResult(Activity.RESULT_CANCELED, intentBack);
                        finish();
                    }
                    else {
                        //TODO insert card name and list card contents to database

                        Intent intentBack = new Intent();
                        intentBack.putExtra(FlipCardAddActivity.NEW_CARD_NAME, editTextCardName.getText().toString());
                        intentBack.putExtra(FlipCardAddActivity.IS_ADD_OR_EDIT, "ADD");
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
                else if (isAddOrEdit.equals("EDIT")) {
                    if (listCardContents.size() == 0 && editTextCardName.getText().toString().equals("")) {
                        Intent intentBack = new Intent();
                        setResult(Activity.RESULT_CANCELED, intentBack);
                        finish();
                    }
                    else {
                        //TODO update card name and list card contents to database

                        Intent intentBack = new Intent();
                        intentBack.putExtra(FlipCardAddActivity.NEW_CARD_NAME, editTextCardName.getText().toString());
                        intentBack.putExtra(FlipCardAddActivity.IS_ADD_OR_EDIT, "EDIT");
                        intentBack.putExtra(FlipCardAddActivity.EDITED_CARD_POSITION, editedCardPosition);
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
            }
        });

        editTextCardName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextCardName.clearFocus();
                }
                return false;
            }
        });

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardAddContentDialog cardAddContentDialog = new CardAddContentDialog(FlipCardAddActivity.this);
                cardAddContentDialog.show();

                cardAddContentDialog.confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newCardContent = cardAddContentDialog.editTextCardContent.getText().toString();
                        if (newCardContent == null || newCardContent.equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Vui lòng nhập nội dung thẻ bài", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            listCardContents.add(0, newCardContent);
                            cardAddItemAdapter.notifyDataSetChanged();
                            gridView.invalidateViews();
                            cardAddContentDialog.dismiss();
                        }
                    }
                });

                cardAddContentDialog.cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardAddContentDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public void setPresenter(FlipCardAddContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickConfirmButton(int position, String newCardContent) {
        listCardContents.set(position, newCardContent);
        cardAddItemAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }

    @Override
    public void onClickDeleteButton(int position) {
        listCardContents.remove(position);
        cardAddItemAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }

}