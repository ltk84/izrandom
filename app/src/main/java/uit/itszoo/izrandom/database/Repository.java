package uit.itszoo.izrandom.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class Repository {
    private UserConfigDAO userConfigDAO;
    private LiveData<UserConfiguration> userConfig;

    private CardCollectionDAO cardCollectionDAO;

    private CardDAO cardDAO;

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

        cardCollectionDAO = appDatabase.cardCollectionDAO();
        cardDAO = appDatabase.cardDAO();

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

    public LiveData<UserConfiguration> getUserConfiguration() {
        return userConfig;
    }

    public List<CardCollectionModel> getAllCardCollections() {
        List<CardCollectionModel> listCardCollectionModel = new ArrayList<>();
        try {
            listCardCollectionModel = AppDatabase.dbExecutor.submit(() -> cardCollectionDAO.getAllCardCollections()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return listCardCollectionModel;
    }

    public void insertCardCollection(CardCollectionModel cardCollectionModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardCollectionDAO.insertCardCollection(cardCollectionModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCardCollection(CardCollectionModel cardCollectionModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardCollectionDAO.updateCardCollection(cardCollectionModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCardCollection(CardCollectionModel cardCollectionModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardCollectionDAO.deleteCardCollection(cardCollectionModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CardModel> getCardModelsByCollectionId(String collectionId) {
        List<CardModel> listCardModel = new ArrayList<>();
        try {
            listCardModel = AppDatabase.dbExecutor.submit(() -> cardDAO.getCardsByCollectionId(collectionId)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return listCardModel;
    }

    public void deleteAllCardsInCollection(String collectionId) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardDAO.deleteAllCardsInCollection(collectionId);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertCard(CardModel cardModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardDAO.insertCard(cardModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCard(CardModel cardModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardDAO.updateCard(cardModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(CardModel cardModel) {
        try {
            AppDatabase.dbExecutor.execute(() -> {
                cardDAO.deleteCard(cardModel);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
