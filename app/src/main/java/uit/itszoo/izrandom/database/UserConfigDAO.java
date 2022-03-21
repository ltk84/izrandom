package uit.itszoo.izrandom.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserConfigDAO {
    @Insert
    public void insertUserConfig(UserConfiguration... userConfigurations);

    @Query("UPDATE userConfig SET arrow=:arrow")
    public void updateArrow(int arrow);

    @Query("SELECT * FROM userConfig")
    List<UserConfiguration> getUserConfig();
}
