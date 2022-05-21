package uit.itszoo.izrandom.play_module.question_for_fun;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class QuestionForFunActivity extends AppCompatActivity implements QuestionForFunContract.View {
    private static boolean isCardFlipped = false;
    private static boolean isCardFlipping = false;
    private int indexCard = 0;

    List<String> listCardContents = new ArrayList<>();

    QuestionForFunContract.Presenter presenter;
    ImageButton backButton;

    Button swipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_for_fun);

        presenter = new QuestionForFunPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        //TODO get listCardContents from database
        for (int i = 0; i < 200; i++) {
            listCardContents.add("What did you want to be when you grew up? " + String.valueOf(i));
        }

        initView();
        setListenerForView();
        setUpCardStackView();

    }

    private void initView() {
        backButton = findViewById(R.id.bb_question_for_fun);

        swipeButton = findViewById(R.id.btn_swipe_question_for_fun);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("QuestionForFunActivity", "swipeButton onClick called");

            }
        });

    }

    private void setUpCardStackView() {

    }

    @Override
    public void setPresenter(QuestionForFunContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
