package uit.itszoo.izrandom.random_module.random_integer;


import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;

public class RandomIntegerPresenter implements RandomIntegerContract.Presenter {
    private final RandomIntegerContract.View view;
    private ArrayList<Integer> listCusNum = new ArrayList<Integer>();
    private Repository repository;
    private LiveData<UserConfiguration> userConfig;

    public RandomIntegerPresenter(Context context, RandomIntegerContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public ArrayList<Integer> getListCusNum (){
        return this.listCusNum;
    }

    @Override
    public void setListCusNum(ArrayList<Integer> listNum)
    {
        this.listCusNum = listNum;

    }
    @Override
    public void start() {
    }
}
