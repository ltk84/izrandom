package uit.itszoo.izrandom.database;

import android.content.Context;

import java.util.concurrent.TimeUnit;

public class Repository {
    private UserConfigDAO userConfigDAO;
    private UserConfiguration userConfiguration;
    private AppDatabase appDatabase;

    private static volatile Repository instance;

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }

        return instance;
    }

    public Repository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        userConfigDAO = appDatabase.userConfigDAO();
        AppDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userConfiguration = userConfigDAO.getUserConfig().get(0);
            }
        });

//        try {
//            AppDatabase.dbExecutor.awaitTermination(2, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void changeArrow(int arrow) {
        AppDatabase.dbExecutor.execute(() -> {
            userConfigDAO.updateArrow(arrow);
        });

        try {
            AppDatabase.dbExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UserConfiguration getUserConfiguration() {
        return userConfiguration;
    }
}
