package uit.itszoo.izrandom.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

@Dao
public interface CardDAO {
    @Insert
    public void insertCard(CardModel cardModel);

    @Update
    public void updateCard(CardModel cardModel);

    @Query("SELECT * FROM card WHERE cardCollectionId =:cardCollectionId ORDER BY createdAtInMs DESC")
    public List<CardModel> getCardsByCollectionId(String cardCollectionId);

    @Query("DELETE FROM card WHERE cardCollectionId =:cardCollectionId")
    public void deleteAllCardsInCollection(String cardCollectionId);

}
