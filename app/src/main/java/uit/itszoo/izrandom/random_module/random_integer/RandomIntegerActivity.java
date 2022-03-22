package uit.itszoo.izrandom.random_module.random_integer;

import static java.lang.Integer.parseInt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import uit.itszoo.izrandom.R;

public class RandomIntegerActivity extends AppCompatActivity implements RandomIntegerContract.View{
    List<Integer> listNumsResult = new ArrayList<>();
    RecyclerviewAdapter recyclerviewAdapter;
    TextView textGuide;
    TextView numOfInteger;
    EditText min;
    EditText max;
    ImageButton up;
    ImageButton down;
    RandomIntegerContract.Presenter ranNumPresenter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_integer);
        ranNumPresenter = new RandomIntegerPresenter(this);
        setPresenter(ranNumPresenter);
        initView();
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
    public void setListenerForView()
    {
        up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(listNumsResult.size() < 4)
                {
                    listNumsResult.add(1);
                    numOfInteger.setText(Integer.toString(listNumsResult.size()));
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), listNumsResult.size() ==1?1:2));
                    recyclerviewAdapter.setList(listNumsResult);
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
                    recyclerviewAdapter.setList(listNumsResult);
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), listNumsResult.size() ==1?1:2));
                }
            }
        });
    }

    @Override
    public void setPresenter(RandomIntegerContract.Presenter presenter) {
        this.ranNumPresenter = presenter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Random rand = new Random();
        int minNum = parseInt(min.getText().toString());
        int maxNum = parseInt(max.getText().toString());
        for(int i = 0 ; i < listNumsResult.size(); i++)
        {
            listNumsResult.set(i,(int)(Math.random()*(maxNum-minNum+1)+minNum));
        }
        recyclerviewAdapter.setList(listNumsResult);
        recyclerView.setAdapter(recyclerviewAdapter);
        return super.onTouchEvent(event);
    }
}