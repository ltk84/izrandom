package uit.itszoo.izrandom.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class Repository {
    private UserConfigDAO userConfigDAO;
    private LiveData<UserConfiguration> userConfig;

    private SliceDAO sliceDAO;
    private LiveData<List<LuckyWheelSlice>> sliceList;

    private WheelDAO wheelDAO;
    private LiveData<List<LuckyWheelData>> wheelList;

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

        sliceDAO = appDatabase.sliceDAO();
        sliceList = sliceDAO.getAllSlices();

        wheelDAO = appDatabase.wheelDAO();
        wheelList = wheelDAO.getAllWheels();

        // trigger để mở database => tránh việc lỗi khi sử dụng LiveData khi db chưa được open
        appDatabase.query("SELECT 1", null);
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

    public void changeChooserTheme(String cThemeId) {
        AppDatabase.dbExecutor.execute(() -> {
            userConfigDAO.updateChooserTheme(cThemeId);
        });
    }

    public void changeChosenWheel(String wheelID) {
        AppDatabase.dbExecutor.execute(() -> userConfigDAO.updateWheel(wheelID));
    }

    public void addSlice(LuckyWheelSlice... slices) {
        AppDatabase.dbExecutor.execute(() -> {
            sliceDAO.insertSlice(slices);
        });
    }

    public void updateSlice(LuckyWheelSlice slice) {
        AppDatabase.dbExecutor.execute(() -> {
            sliceDAO.updateSlice(slice);
        });
    }

    public void deleteSlice(LuckyWheelSlice... slice) {
        AppDatabase.dbExecutor.execute(() -> sliceDAO.deleteSlice(slice));
    }

    public LiveData<List<LuckyWheelSlice>> getAllSlices() {
        return sliceList;
    }

    public LiveData<List<LuckyWheelData>> getAllWheels() {
        return wheelList;
    }

    public LuckyWheelData getWheelByID(String id) {
        LuckyWheelData wheelData = null;
        try {
            wheelData = AppDatabase.dbExecutor.submit(() -> wheelDAO.getWheelByID(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return wheelData;
    }

    public List<LuckyWheelSlice> getSlicesByWheelID(String wheelID) {
        List<LuckyWheelSlice> list = new ArrayList<>();
        try {
            list = AppDatabase.dbExecutor.submit(() -> sliceDAO.getSliceByWheelID(wheelID)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LiveData<UserConfiguration> getUserConfiguration() {
        return userConfig;
    }
}
