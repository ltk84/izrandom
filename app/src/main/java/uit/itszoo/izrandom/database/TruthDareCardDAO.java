package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCard;

@Dao
public interface TruthDareCardDAO {
    @Insert
    public void insertCard(TruthDareCard card);

    @Update
    public void updateCard(TruthDareCard card);

    @Delete
    public void deleteCard(TruthDareCard card);

    @Query("SELECT * FROM truthDareCard")
    public LiveData<List<TruthDareCard>> getAllCards();
}
