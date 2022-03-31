package uit.itszoo.izrandom.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

public class Repository {
    private UserConfigDAO userConfigDAO;
    private LiveData<UserConfiguration> userConfig;

    private static volatile Repository instance;

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }

        return instance;
    }

    public Repository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userConfigDAO = appDatabase.userConfigDAO();
        userConfig = userConfigDAO.getUserConfig();
    }

    public void changeArrow(String arrowId) {
        AppDatabase.dbExecutor.execute(() -> {
            userConfigDAO.updateArrow(arrowId);
        });
    }

    public void changeDice(String diceId) {
        AppDatabase.dbExecutor.execute(() -> {
            userConfigDAO.updateDice(diceId);
        });
    }

    public void changeCoin(String coinId) {
        AppDatabase.dbExecutor.execute(() -> {
            userConfigDAO.updateCoin(coinId);
        });
    }

    public LiveData<UserConfiguration> getUserConfiguration() {
        return userConfig;
    }
}
