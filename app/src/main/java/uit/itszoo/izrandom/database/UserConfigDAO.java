package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserConfigDAO {
    @Insert
    public void insertUserConfig(UserConfiguration... userConfigurations);

    @Query("UPDATE userConfig SET arrow=:arrow")
    public void updateArrow(int arrow);

//    @Query("UPDATE userConfig SET dice=:dice")
//    public void updateDice(String dice);

    @Query("SELECT * FROM userConfig LIMIT 1")
    public LiveData<UserConfiguration> getUserConfig();
}
