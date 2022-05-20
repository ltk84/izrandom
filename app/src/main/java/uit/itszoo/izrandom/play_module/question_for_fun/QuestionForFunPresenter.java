package uit.itszoo.izrandom.play_module.question_for_fun;

import android.content.Context;

import uit.itszoo.izrandom.database.Repository;

public class QuestionForFunPresenter implements QuestionForFunContract.Presenter {
    private final QuestionForFunContract.View view;

    private Repository repository;

    public QuestionForFunPresenter(Context context, QuestionForFunContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
    }

    @Override
    public void start() {

    }
}
