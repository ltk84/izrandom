package uit.itszoo.izrandom.random_module.random_integer;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class RandomIntegerActivity extends AppCompatActivity implements RandomIntegerContract.View{
    public static final String CURRENT_CUS_NUM = "CURRENT_CUS_NUM";
    List<Integer> listNumsResult = new ArrayList<>();
    MutableLiveData<List<Integer>>  listMutableLiveData = new MutableLiveData<List<Integer>>();
    RecyclerviewAdapter recyclerviewAdapter;
    TextView textGuide;
    TextView numOfInteger;
    EditText min;
    EditText max;
    ImageButton up;
    ImageButton down;
    RandomIntegerContract.Presenter ranNumPresenter;
    RecyclerView recyclerView;
    ImageButton backButton;
    ImageButton customScreenButton;
//    public static final RandomIntegerCustomActivity cus  = new RandomIntegerCustomActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_integer);
        ranNumPresenter = new RandomIntegerPresenter(getApplicationContext(), this);
        setPresenter(ranNumPresenter);
        initView();
        listMutableLiveData.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                recyclerviewAdapter.setList(integers);
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), integers.size() ==1?1:2));
                recyclerView.setAdapter(recyclerviewAdapter);
            }
        });
        setListenerForView();
    }

    private void initView() {
        textGuide = findViewById(R.id.txt_guide);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        numOfInteger = findViewById(R.id.numOfInteger);
        min = findViewById(R.id.editMinNum);
        max = findViewById(R.id.editMaxNum);
        listNumsResult.add(1);
        listMutableLiveData.setValue(listNumsResult);
        backButton = findViewById(R.id.bb_rand_integer_cus);
        customScreenButton = findViewById(R.id.rand_integer_cus);
        setRecyclerview();
    }
    private void setRecyclerview()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, listNumsResult.size() ==1?1:2){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerviewAdapter = new RecyclerviewAdapter(listNumsResult);
        recyclerView.setAdapter(recyclerviewAdapter);
    }
    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<Integer> newListCusNum = data.getIntegerArrayListExtra(RandomIntegerCustomActivity.NEW_CUS_NUM);
                        if (newListCusNum != null) {
                            ranNumPresenter.setListCusNum(newListCusNum);
                        }

                    }
                }
            });
    public void setListenerForView()
    {
        up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(listNumsResult.size() < 4)
                {
                    listNumsResult.add(1);
                    numOfInteger.setText(Integer.toString(listNumsResult.size()));
                    listMutableLiveData.setValue(listNumsResult);
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(listNumsResult.size() > 1)
                {
                    int index = listNumsResult.size()-1;
                    listNumsResult.remove(index);
                    numOfInteger.setText(Integer.toString(listNumsResult.size()));
                    listMutableLiveData.setValue(listNumsResult);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        customScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), RandomIntegerCustomActivity.class);

                intentToCustom.putExtra(RandomIntegerActivity.CURRENT_CUS_NUM, ranNumPresenter.getListCusNum());

                intentLauncher.launch(intentToCustom);
            }
        });
    }

    @Override
    public void setPresenter(RandomIntegerContract.Presenter presenter) {
        this.ranNumPresenter = presenter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                executeRandom();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public void executeRandom() {
        int minNum = parseInt(min.getText().toString());
        int maxNum = parseInt(max.getText().toString());
        int time = 0;

        while (time < 50)
        {
            long start = System.currentTimeMillis();
            for(int i = 0 ; i < listNumsResult.size(); i++)
            {
                Integer randomResult = (int)(Math.random()*(maxNum-minNum+1)+minNum);
                if (!ranNumPresenter.getListCusNum().contains(randomResult)) {
                    listNumsResult.set(i, randomResult);
                }

            }
            listMutableLiveData.setValue(listNumsResult);
            time += 1;
        }

    }

}