package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;

@Dao
public interface CardCollectionDAO {
    @Insert
    public void insertCardCollection(CardCollectionModel cardCollectionModel);

    @Update
    public void updateCardCollection(CardCollectionModel cardCollectionModel);

    @Delete
    public void deleteCardCollection(CardCollectionModel cardCollectionModel);

    @Query("SELECT * FROM cardCollection WHERE id =:id")
    public CardCollectionModel getCardCollectionById(String id);

    @Query("SELECT * FROM cardCollection ORDER BY createdAtInMs DESC")
    public List<CardCollectionModel> getAllCardCollections();
}
