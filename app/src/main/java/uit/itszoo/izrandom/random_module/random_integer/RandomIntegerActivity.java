package uit.itszoo.izrandom.random_module.random_integer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import uit.itszoo.izrandom.R;

public class RandomIntegerActivity extends AppCompatActivity implements RandomIntegerContract.View{
    List<Integer> listNumsResult = new ArrayList<>();
    RecyclerviewAdapter recyclerviewAdapter;
    TextView textGuide;
    TextView textView;
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
        listNumsResult.add(1);
        setRecyclerview();
    }
    private void setRecyclerview()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, listNumsResult.size() ==1?1:2));
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
                    setRecyclerview();
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
                    setRecyclerview();
                }
            }
        });
    }

    @Override
    public void setPresenter(RandomIntegerContract.Presenter presenter) {
        this.ranNumPresenter = presenter;
    }
}