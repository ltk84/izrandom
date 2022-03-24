package uit.itszoo.izrandom.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;


@Database(entities = {UserConfiguration.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "userConfig.db";

    private static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = createInstance(context);
        }
        return instance;
    }


    public AppDatabase() {
    }

    private static AppDatabase createInstance(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).addCallback(initDB).build();
    }

    private static RoomDatabase.Callback initDB = new Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("ONCREATE", "Database has been created.");
            dbExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    UserConfiguration userConfiguration = new UserConfiguration();
                    userConfiguration.arrowId = ArrowSource.arrows.get(0).getId();
                    userConfiguration.diceId = DiceSource.dices.get(0).getId();

                    instance.userConfigDAO().insertUserConfig(userConfiguration);
                }
            });

            try {
                dbExecutor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("ONOPEN", "Database has been opened.");
        }
    };

    public abstract UserConfigDAO userConfigDAO();
}
