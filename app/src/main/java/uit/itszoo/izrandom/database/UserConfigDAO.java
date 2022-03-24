package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserConfigDAO {
    @Insert
    public void insertUserConfig(UserConfiguration... userConfigurations);

    @Query("UPDATE userConfig SET arrowId=:arrowId")
    public void updateArrow(String arrowId);

    @Query("UPDATE userConfig SET diceId=:diceId")
    public void updateDice(String diceId);

    @Query("SELECT * FROM userConfig LIMIT 1")
    public LiveData<UserConfiguration> getUserConfig();
}