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
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;


@Database(entities = {UserConfiguration.class, LuckyWheelSlice.class, LuckyWheelData.class, CardCollectionModel.class, CardModel.class}, version = 1)
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
                initCardCollection();
                initLuckyWheelSlice();
                initLuckyWheel();
                initUserConfig();
            });

        }

        @Override
        public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("ONOPEN", "Database has been opened.");
        }
    };

    private static void initCardCollection() {
        CardCollectionModel cardCollectionModel1 = new CardCollectionModel(UUID.randomUUID().toString(), "Bộ câu hỏi vui để hiểu bản thân", System.currentTimeMillis());
        ArrayList<CardModel> listCardModels1 = new ArrayList<>(
                Arrays.asList(
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Mùi yêu thích của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn cảm thấy biết ơn vì điều gì trong cuộc sống?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Ký ức đáng quý nhất của bạn là gì", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Điều thành công nhất bạn đã đạt được là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn thấy điều gì đáng quý nhất trong tình bạn?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Theo bạn, một ngày hoàn hảo là như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Ước mơ thời ấu thơ của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn tự nhận xét bản thân là người như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn muốn trở thành mẫu người như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Đâu là nỗi sợ lớn nhất của bạn?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Mục tiêu ngắn hạn, dài hạn của bạn là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Nếu có một điều ước, bạn sẽ ước điều gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn thích và muốn thử hoạt động mới nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn thích và không thích nhất điều gì ở bản thân?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Ai là người quan trọng nhất với bạn ở hiện tại?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Bạn thích làm điều gì trong thời gian rảnh?", System.currentTimeMillis())
                )
        );
        instance.cardCollectionDAO().insertCardCollection(cardCollectionModel1);
        for (CardModel cardModel : listCardModels1) {
            instance.cardDAO().insertCard(cardModel);
        }

        CardCollectionModel cardCollectionModel2 = new CardCollectionModel(UUID.randomUUID().toString(), "Bộ câu hỏi ngớ ngẩn hài hước cho bạn bè", System.currentTimeMillis());
        ArrayList<CardModel> listCardModels2 = new ArrayList<>(
                Arrays.asList(
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Nếu động vật bỗng nhiên biết nói chuyện, bạn nghĩ thú cưng nhà bạn sẽ nói gì với bạn?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Nếu bạn được tạo ra một ngày lễ mới, bạn sẽ đặt tên cho nó là gì và nó sẽ được tổ chức như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Điều gì làm cho một câu hỏi trở thành một câu hỏi?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Bạn đã bị đưa vào một nhà thương điên. Bạn nói gì để chứng minh rằng bạn không thuộc về nơi này?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Thứ gì đó không thực sự có mùi thơm nhưng bạn vẫn muốn ngửi nó?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Theo bạn, con gà có trước hay quả trứng có trước?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Cần bao nhiêu con gà để giết một con voi?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Tại sao bạn ngủ trong phòng ngủ mà không phải nhà bếp?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Nếu bạn có thể đưa ra một quy tắc cho một ngày và mọi người phải tuân theo nó, thì đó sẽ là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Tại sao mọi người không để quần áo trong tủ lạnh?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Nếu bạn được chọn 1 hình xăm cho người bên cạnh, bạn sẽ chọn hình gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Điều kỳ lạ nhất bạn từng thấy ở nhà người khác là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Bạn nghĩ cách tồi tệ nhất để chết là gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Bạn thường đi ị như thế nào?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Nếu bạn được thay đổi bất kỳ một bộ phận trên cơ thể mình, bạn sẽ thay đổi điều gì?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Tại sao “Không” của một cô gái có nghĩa là “Có” và “Có” có nghĩa là “Không”?", System.currentTimeMillis())
                )
        );
        instance.cardCollectionDAO().insertCardCollection(cardCollectionModel2);
        for (CardModel cardModel : listCardModels2) {
            instance.cardDAO().insertCard(cardModel);
        }
    }

    private static void initUserConfig() {
        UserConfiguration userConfiguration = new UserConfiguration();
        userConfiguration.arrowId = ArrowSource.arrows.get(0).getId();
        userConfiguration.diceId = DiceSource.dices.get(0).getId();
        userConfiguration.coinId = CoinSource.coins.get(0).getId();
        userConfiguration.chooserThemeId = ChooserSource.themes.get(0).getId();
        userConfiguration.wheelID = LuckyWheelSource.luckyWheelList.get(0).getId();

        instance.userConfigDAO().insertUserConfig(userConfiguration);
    }

    private static void initLuckyWheelSlice() {
        for (LuckyWheelSlice slice : LuckyWheelSource.slices) {
            instance.sliceDAO().insertSlice(slice);
        }
    }

    private static void initLuckyWheel() {
        for (LuckyWheelData wheel : LuckyWheelSource.luckyWheelList) {
            instance.wheelDAO().insertWheel(wheel);
        }
    }

    abstract UserConfigDAO userConfigDAO();

    abstract CardCollectionDAO cardCollectionDAO();

    abstract CardDAO cardDAO();

    abstract SliceDAO sliceDAO();

    abstract WheelDAO wheelDAO();
}
