package uit.itszoo.izrandom.random_module.random_integer;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface RandomIntegerContract {
    interface Presenter extends BasePresenter {
        ArrayList<Integer> getListCusNum ();
        void setListCusNum(ArrayList<Integer> listNum);
    }

    interface View extends BaseView<Presenter> {
        void executeRandom();

    }
}
