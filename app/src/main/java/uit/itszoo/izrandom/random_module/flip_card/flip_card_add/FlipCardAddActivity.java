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
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.CardContentDialog;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.CardItemAdapter;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.FlipCardActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;
import uit.itszoo.izrandom.random_module.random_integer.RandomIntegerActivity;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class FlipCardAddActivity extends AppCompatActivity implements FlipCardAddContract.View, OnCardItemClick {
    public static final String EDITED_CARD_COLLECTION = "EDITED_CARD_COLLECTION";
    public static final String IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT";
    public static final String EDITED_CARD_POSITION = "EDITED_CARD_POSITION";

    // List to store actions for IS_ADD_OR_EDIT = EDIT
    List<CardActionDataHelper> listCardActionHistory = new ArrayList<>();

    String isAddOrEdit;
    CardCollectionModel cardCollectionModel;
    int editedCardPosition;

    List<CardModel> listCardModels = new ArrayList<>();
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

        presenter = new FlipCardAddPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        isAddOrEdit = getIntent().getExtras().getString(FlipCardMenuActivity.IS_ADD_OR_EDIT);
        cardCollectionModel = (CardCollectionModel) getIntent().getSerializableExtra(FlipCardMenuActivity.CARD_COLLECTION_TO_EDIT);

        if (isAddOrEdit.equals("EDIT")) {
            editedCardPosition = getIntent().getExtras().getInt(FlipCardMenuActivity.CARD_POSITION_TO_EDIT);
            listCardModels = presenter.getListCardModelByCollectionId(cardCollectionModel.getId());
        }

        initView();
        setListenerForView();

        cardAddItemAdapter = new CardAddItemAdapter(this, FlipCardAddActivity.this, listCardModels, this);
        gridView.setAdapter(cardAddItemAdapter);

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

            editTextCardName.setText(cardCollectionModel.getCardName().toString());
        }
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listCardModels.size() == 0 && editTextCardName.getText().toString().equals("")) {
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
                    if (listCardModels.size() == 0 && editTextCardName.getText().toString().equals("")) {
                        Intent intentBack = new Intent();
                        setResult(Activity.RESULT_CANCELED, intentBack);
                        finish();
                    }
                    else if (listCardModels.size() == 0 && !editTextCardName.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Vui lòng tạo thẻ bài", Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextCardName.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Vui lòng nhập tên thẻ bài", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        cardCollectionModel.setCardName(editTextCardName.getText().toString());
                        presenter.insertCardCollection(cardCollectionModel);
                        for (CardModel cardModel : listCardModels) {
                            presenter.insertCard(cardModel);
                        }

                        Intent intentBack = new Intent();
                        intentBack.putExtra(FlipCardAddActivity.EDITED_CARD_COLLECTION, cardCollectionModel);
                        intentBack.putExtra(FlipCardAddActivity.IS_ADD_OR_EDIT, "ADD");
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
                else if (isAddOrEdit.equals("EDIT")) {
                    if (listCardModels.size() == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Vui lòng tạo thẻ bài", Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextCardName.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Vui lòng nhập tên thẻ bài", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        cardCollectionModel.setCardName(editTextCardName.getText().toString());
                        presenter.updateCardCollection(cardCollectionModel);
                        for (CardActionDataHelper cardActionDataHelper : listCardActionHistory) {
                            if (cardActionDataHelper.action.equals("Insert")) {
                                presenter.insertCard(cardActionDataHelper.cardModel);
                            }
                            else if (cardActionDataHelper.action.equals("Update")) {
                                presenter.updateCard(cardActionDataHelper.cardModel);
                            }
                            else if (cardActionDataHelper.action.equals("Delete")) {
                                presenter.deleteCard(cardActionDataHelper.cardModel);
                            }
                        }

                        Intent intentBack = new Intent();
                        intentBack.putExtra(FlipCardAddActivity.EDITED_CARD_COLLECTION, cardCollectionModel);
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
                            CardModel newCardModel = new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), cardAddContentDialog.editTextCardContent.getText().toString(), System.currentTimeMillis());
                            listCardModels.add(0, newCardModel);
                            try {
                                listCardActionHistory.add(new CardActionDataHelper("Insert", newCardModel.clone()));
                            }
                            catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
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

    // card add content dialog on confirm button click
    @Override
    public void onClickConfirmButton(int position, String newCardContent) {
        listCardModels.get(position).setCardContent(newCardContent);
        try {
            // Check if edited card is in list
            // if true than update list item card model
            // if false add to list
            boolean isCardInList = false;
            int cardIndexInList = -1;
            String cardIdToFind = listCardModels.get(position).getId();
            for (int i = 0; i < listCardActionHistory.size(); i++) {
                if (listCardActionHistory.get(i).cardModel.getId().equals(cardIdToFind)) {
                    isCardInList = true;
                    cardIndexInList = i;
                    break;
                }
            }
            if (isCardInList) {
                listCardActionHistory.get(cardIndexInList).setCardModel(listCardModels.get(position).clone());
            }
            else {
                listCardActionHistory.add(new CardActionDataHelper("Update", listCardModels.get(position).clone()));
            }
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        cardAddItemAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }

    // gridView card delete button onclick
    @Override
    public void onClickDeleteButton(int position) {
        try {
            // Check if deleted card is in list
            // if true than update list item action to delete
            // if false add to list
            boolean isCardInList = false;
            int cardIndexInList = -1;
            String cardIdToFind = listCardModels.get(position).getId();
            for (int i = 0; i < listCardActionHistory.size(); i++) {
                if (listCardActionHistory.get(i).cardModel.getId().equals(cardIdToFind)) {
                    isCardInList = true;
                    cardIndexInList = i;
                    break;
                }
            }
            if (isCardInList) {
                listCardActionHistory.get(cardIndexInList).action = "Delete";
            }
            else {
                listCardActionHistory.add(new CardActionDataHelper("Delete", listCardModels.get(position).clone()));
            }


        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        listCardModels.remove(position);
        cardAddItemAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }

}