package uit.itszoo.izrandom.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCard;
import uit.itszoo.izrandom.random_module.chooser.source.ChooserSource;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;
import uit.itszoo.izrandom.random_module.flip_coin.source.CoinSource;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;


@Database(entities = {UserConfiguration.class, LuckyWheelSlice.class, LuckyWheelData.class, CardCollectionModel.class, CardModel.class, TruthDareCard.class}, version = 1)
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
                initTruthDareCards();
            });

        }

        @Override
        public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("ONOPEN", "Database has been opened.");
        }
    };

    private static void initTruthDareCards() {
        TruthDareCard card = new TruthDareCard(UUID.randomUUID().toString(), "Chut vi cua say me", 1);
        instance.tdCardDAO().insertCard(card);
    }

    private static void initCardCollection() {
        CardCollectionModel cardCollectionModel1 = new CardCollectionModel(UUID.randomUUID().toString(), "B??? c??u h???i vui ????? hi???u b???n th??n", System.currentTimeMillis());
        ArrayList<CardModel> listCardModels1 = new ArrayList<>(
                Arrays.asList(
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "M??i y??u th??ch c???a b???n l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n c???m th???y bi???t ??n v?? ??i???u g?? trong cu???c s???ng?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "K?? ???c ????ng qu?? nh???t c???a b???n l?? g??", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "??i???u th??nh c??ng nh???t b???n ???? ?????t ???????c l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n th???y ??i???u g?? ????ng qu?? nh???t trong t??nh b???n?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Theo b???n, m???t ng??y ho??n h???o l?? nh?? th??? n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "?????c m?? th???i ???u th?? c???a b???n l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n t??? nh???n x??t b???n th??n l?? ng?????i nh?? th??? n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n mu???n tr??? th??nh m???u ng?????i nh?? th??? n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "????u l?? n???i s??? l???n nh???t c???a b???n?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "M???c ti??u ng???n h???n, d??i h???n c???a b???n l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "N???u c?? m???t ??i???u ?????c, b???n s??? ?????c ??i???u g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n th??ch v?? mu???n th??? ho???t ?????ng m???i n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n th??ch v?? kh??ng th??ch nh???t ??i???u g?? ??? b???n th??n?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "Ai l?? ng?????i quan tr???ng nh???t v???i b???n ??? hi???n t???i?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel1.getId(), "B???n th??ch l??m ??i???u g?? trong th???i gian r???nh?", System.currentTimeMillis())
                )
        );
        instance.cardCollectionDAO().insertCardCollection(cardCollectionModel1);
        for (CardModel cardModel : listCardModels1) {
            instance.cardDAO().insertCard(cardModel);
        }

        CardCollectionModel cardCollectionModel2 = new CardCollectionModel(UUID.randomUUID().toString(), "B??? c??u h???i ng??? ng???n h??i h?????c cho b???n b??", System.currentTimeMillis());
        ArrayList<CardModel> listCardModels2 = new ArrayList<>(
                Arrays.asList(
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "N???u ?????ng v???t b???ng nhi??n bi???t n??i chuy???n, b???n ngh?? th?? c??ng nh?? b???n s??? n??i g?? v???i b???n?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "N???u b???n ???????c t???o ra m???t ng??y l??? m???i, b???n s??? ?????t t??n cho n?? l?? g?? v?? n?? s??? ???????c t??? ch???c nh?? th??? n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "??i???u g?? l??m cho m???t c??u h???i tr??? th??nh m???t c??u h???i?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "B???n ???? b??? ????a v??o m???t nh?? th????ng ??i??n. B???n n??i g?? ????? ch???ng minh r???ng b???n kh??ng thu???c v??? n??i n??y?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Th??? g?? ???? kh??ng th???c s??? c?? m??i th??m nh??ng b???n v???n mu???n ng???i n???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "Theo b???n, con g?? c?? tr?????c hay qu??? tr???ng c?? tr?????c?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "C???n bao nhi??u con g?? ????? gi???t m???t con voi?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "T???i sao b???n ng??? trong ph??ng ng??? m?? kh??ng ph???i nh?? b???p?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "N???u b???n c?? th??? ????a ra m???t quy t???c cho m???t ng??y v?? m???i ng?????i ph???i tu??n theo n??, th?? ???? s??? l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "T???i sao m???i ng?????i kh??ng ????? qu???n ??o trong t??? l???nh?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "N???u b???n ???????c ch???n 1 h??nh x??m cho ng?????i b??n c???nh, b???n s??? ch???n h??nh g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "??i???u k??? l??? nh???t b???n t???ng th???y ??? nh?? ng?????i kh??c l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "B???n ngh?? c??ch t???i t??? nh???t ????? ch???t l?? g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "B???n th?????ng ??i ??? nh?? th??? n??o?", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "N???u b???n ???????c thay ?????i b???t k??? m???t b??? ph???n tr??n c?? th??? m??nh, b???n s??? thay ?????i ??i???u g???", System.currentTimeMillis()),
                        new CardModel(UUID.randomUUID().toString(), cardCollectionModel2.getId(), "T???i sao ???Kh??ng??? c???a m???t c?? g??i c?? ngh??a l?? ???C????? v?? ???C????? c?? ngh??a l?? ???Kh??ng????", System.currentTimeMillis())
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

    abstract TruthDareCardDAO tdCardDAO();
}
