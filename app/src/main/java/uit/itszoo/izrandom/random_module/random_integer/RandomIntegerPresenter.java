package uit.itszoo.izrandom.random_module.random_integer;


import java.util.ArrayList;
import java.util.List;

public class RandomIntegerPresenter implements RandomIntegerContract.Presenter {
    private final RandomIntegerContract.View view;
    private List<Integer> listCusNum = new ArrayList<Integer>();

    public RandomIntegerPresenter(RandomIntegerContract.View view) {
        this.view = view;
    }

    public  List<Integer> getListCusNum (){
        return this.listCusNum;
    }
    public void changListCus(List<Integer> listNum)
    {
        this.listCusNum = listNum;

    }
    @Override
    public void start() {
    }
}
