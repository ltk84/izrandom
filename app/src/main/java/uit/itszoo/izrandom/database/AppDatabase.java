package uit.itszoo.izrandom.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uit.itszoo.izrandom.random_module.chooser.source.ChooserSource;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;
import uit.itszoo.izrandom.random_module.flip_coin.source.CoinSource;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;


@Database(entities = {UserConfiguration.class, CardCollectionModel.class, CardModel.class}, version = 1)
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

            dbExecutor.execute(() -> {
                UserConfiguration userConfiguration = new UserConfiguration();
                userConfiguration.arrowId = ArrowSource.arrows.get(0).getId();
                userConfiguration.diceId = DiceSource.dices.get(0).getId();
                userConfiguration.coinId = CoinSource.coins.get(0).getId();
                userConfiguration.chooserThemeId = ChooserSource.themes.get(0).getId();

                instance.userConfigDAO().insertUserConfig(userConfiguration);

                initCardCollection();
            });

        }

        @Override
        public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("ONOPEN", "Database has been opened.");
        }
    };

    private static void initCardCollection() {
        CardCollectionModel cardCollectionModel = new CardCollectionModel(UUID.randomUUID().toString(), "Bộ câu hỏi vui để hiểu bản thân", System.currentTimeMillis());
        ArrayList<CardModel> listCardModels = new ArrayList<>(
                Arrays.asList(
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Mùi yêu thích của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn cảm thấy biết ơn vì điều gì trong cuộc sống?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Ký ức đáng quý nhất của bạn là gì", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Điều thành công nhất bạn đã đạt được là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn thấy điều gì đáng quý nhất trong tình bạn?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Theo bạn, một ngày hoàn hảo là như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Ước mơ thời ấu thơ của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn tự nhận xét bản thân là người như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn muốn trở thành mẫu người như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Đâu là nỗi sợ lớn nhất của bạn?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Mục tiêu ngắn hạn, dài hạn của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Nếu có một điều ước, bạn sẽ ước điều gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn thích và muốn thử hoạt động mới nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn thích và không thích nhất điều gì ở bản thân?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Ai là người quan trọng nhất với bạn ở hiện tại?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel.getId(), "Bạn thích làm điều gì trong thời gian rảnh?", System.currentTimeMillis())
                )
        );
        instance.cardCollectionDAO().insertCardCollection(cardCollectionModel);
        for (CardModel cardModel : listCardModels) {
            instance.cardDAO().insertCard(cardModel);
        }
    }

    abstract UserConfigDAO userConfigDAO();

    abstract CardCollectionDAO cardCollectionDAO();

    abstract CardDAO cardDAO();
}
